package kr.ac.yonsei.maist.domain.log.dao;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.ac.yonsei.maist.domain.log.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static kr.ac.yonsei.maist.domain.language.domain.QLanguage.language;
import static kr.ac.yonsei.maist.domain.log.domain.QUseLog.useLog;
import static kr.ac.yonsei.maist.domain.system.domain.QSystem.system;
import static kr.ac.yonsei.maist.domain.user.domain.QUserPrivate.userPrivate;
import static kr.ac.yonsei.maist.domain.userMachine.domain.QUserMachine.userMachine;

@RequiredArgsConstructor
@Repository
public class UseLogFactory {

    private final JPAQueryFactory queryFactory;

    private NumberTemplate<Integer> weekOfYear() {
        return Expressions.numberTemplate(Integer.class, "DATE_FORMAT({0}, '%v') - DATE_FORMAT(STR_TO_DATE( CONCAT(DATE_FORMAT({0}, '%Y'), DATE_FORMAT({0}, '%m'), '01'), '%Y%m%d'), '%v') + 1", useLog.logDate);
    }

    private NumberTemplate<Integer> dayOfWeek() {
        return Expressions.numberTemplate( Integer.class, "CAST(DATE_FORMAT({0}, '%w') AS integer)", useLog.logDate);
    }

    public List<UseLogDateListResponseDto> findDateList(UseLogDateListRequestDto dto) {
        return queryFactory
                .select(Projections.fields(UseLogDateListResponseDto.class,
                        Expressions.numberTemplate(Integer.class, "CAST(DATE_FORMAT({0}, '%Y') AS integer)", useLog.logDate).as("dateYear"),
                        Expressions.numberTemplate(Integer.class, "CAST(DATE_FORMAT({0}, '%c') AS integer)", useLog.logDate).as("dateMonth"),
                        ExpressionUtils.as(this.weekOfYear(), "weekOfMonth"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(language.content)
                                        .from(language).join(system).on(language.sysCode.eq(system.sysCodeId))
                                        .where(language.languageKind.eq(dto.getLanguageCode()).and(system.name.eq(
                                                Expressions.stringTemplate("CONCAT('month ', {0})",
                                                        Expressions.stringTemplate("DATE_FORMAT({0}, '%c')", useLog.logDate))
                                                )))
                                , "dateMonthName"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(language.content)
                                        .from(language).join(system).on(language.sysCode.eq(system.sysCodeId))
                                        .where(language.languageKind.eq(dto.getLanguageCode()).and(system.name.eq(
                                                Expressions.stringTemplate("CONCAT('week ', {0})",
                                                        this.weekOfYear())
                                        ))),
                                "dateWeekName")
                )).distinct()
                .from(useLog)
                .join(userPrivate).on(useLog.userId.eq(userPrivate.userId))
                .join(userMachine).on(useLog.userId.eq(userMachine.userId))
                .where(userPrivate.name.eq(dto.getUserName()).and(userMachine.machineId.eq(dto.getMachineId())))
                .fetch();
    }

    public List<UseLogListResponseDto> findAllLogData(UseLogRunningRequestDto dto) {

        return queryFactory
                .select(Projections.fields(UseLogListResponseDto.class,
                        useLog.userId.as("userId"),
                        useLog.logDate.as("logDate"),
                        useLog.runningTime.as("runningTime"),
                        useLog.externalTemperature.as("externalTemperature"),
                        useLog.internalTemperature.as("internalTemperature"),
                        useLog.intnsLowFreqPad.as("intnsLowFreqPad"),
                        useLog.content.as("content"),
                        useLog.sysFunc.as("sysFunc")
                ))
                .from(useLog)
                .join(userPrivate).on(useLog.userId.eq(userPrivate.userId))
                .join(userMachine).on(useLog.userId.eq(userMachine.userId))
                .where(userPrivate.name.eq(dto.getUserName()).and(userMachine.machineId.eq(dto.getMachineId())).and(
                        Expressions.stringTemplate("date_format({0}, '%Y-%c')", useLog.logDate).eq(dto.getDateYear() + "-" + dto.getDateMonth())
                ))
                .fetch();
    }

    public List<UseLogRunningResponseDto.UseLogDataByModeResponseDto> findSumLogDataByMode(UseLogRunningRequestDto dto) {
        return queryFactory
                .select(Projections.fields(UseLogRunningResponseDto.UseLogDataByModeResponseDto.class,
                        Expressions.numberTemplate(Integer.class, "SUM({0})", useLog.runningTime).as("sumRunningTimeByWeekOfYear"),
                        useLog.sysFunc.as("sysFunc")
                ))
                .from(useLog)
                .join(userPrivate).on(useLog.userId.eq(userPrivate.userId))
                .join(userMachine).on(useLog.userId.eq(userMachine.userId))
                .where(userPrivate.name.eq(dto.getUserName()).and(userMachine.machineId.eq(dto.getMachineId())).and(
                        Expressions.stringTemplate("date_format({0}, '%Y-%c')", useLog.logDate).eq(dto.getDateYear() + "-" + dto.getDateMonth())
                ).and(
                        this.weekOfYear().eq(dto.getWeekOfMonth())
                ))
                .groupBy(useLog.sysFunc)
                .fetch();
    }

    public List<UseLogRunningResponseDto.UseLogDataByTimeResponseDto> findSumLogDataByTime(UseLogRunningRequestDto dto) {
        NumberTemplate<Integer> groupBy = this.dayOfWeek();
        return queryFactory
                .select(Projections.fields(UseLogRunningResponseDto.UseLogDataByTimeResponseDto.class,
                        Expressions.numberTemplate(Integer.class, "SUM({0})", useLog.runningTime).as("sumRunningTimeByWeekOfYear"),
                        groupBy.as("dayOfWeek")
                ))
                .from(useLog)
                .join(userPrivate).on(useLog.userId.eq(userPrivate.userId))
                .join(userMachine).on(useLog.userId.eq(userMachine.userId))
                .where(userPrivate.name.eq(dto.getUserName()).and(userMachine.machineId.eq(dto.getMachineId())).and(
                        Expressions.stringTemplate("date_format({0}, '%Y-%c')", useLog.logDate).eq(dto.getDateYear() + "-" + dto.getDateMonth())
                ).and(
                        this.weekOfYear().eq(dto.getWeekOfMonth())
                ))
                .groupBy(groupBy)
                .fetch();
    }

    public Integer findTotalLogData(UseLogRunningRequestDto dto) {
        return queryFactory
                .select(Expressions.numberTemplate(Integer.class, "SUM({0})", useLog.runningTime).as("dayOfWeek"))
                .from(useLog)
                .join(userPrivate).on(useLog.userId.eq(userPrivate.userId))
                .join(userMachine).on(useLog.userId.eq(userMachine.userId))
                .where(userPrivate.name.eq(dto.getUserName()).and(userMachine.machineId.eq(dto.getMachineId())).and(
                        Expressions.stringTemplate("date_format({0}, '%Y-%c')", useLog.logDate).eq(dto.getDateYear() + "-" + dto.getDateMonth())
                ).and(
                        this.weekOfYear().eq(dto.getWeekOfMonth())
                )).fetchOne();
    }
}
