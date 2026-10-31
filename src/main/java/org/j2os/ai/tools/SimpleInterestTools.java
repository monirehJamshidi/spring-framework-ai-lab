package org.j2os.ai.tools;

import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.util.Locale;

@Component
public class SimpleInterestTools {

    private static final Logger log = LoggerFactory.getLogger(SimpleInterestTools.class);

    @Tool(
            description = """
        Calculate simple interest and total amount in euros.
        Use this tool for simple-interest calculations.
        The rate is an annual percentage and duration is in months.
        """,
            returnDirect = true
    )
    public String calculateSimpleInterest(
            @ToolParam(description = "Principal amount in euros")
            double principal,

            @ToolParam(description = "Annual interest rate in percent")
            double annualRatePercent,

            @ToolParam(description = "Duration in months")
            int months) {

        log.info(
                "Tool input: principal={}, annualRatePercent={}, months={}",
                principal, annualRatePercent, months
        );

        if (!Double.isFinite(principal) || principal < 0) {
            throw new IllegalArgumentException(
                    "Principal must be a non-negative finite number.");
        }

        if (!Double.isFinite(annualRatePercent)
                || annualRatePercent < 0
                || annualRatePercent > 100) {
            throw new IllegalArgumentException(
                    "Rate must be between 0 and 100 percent.");
        }

        if (months < 0 || months > 1200) {
            throw new IllegalArgumentException(
                    "Months must be between 0 and 1200.");
        }

        double interest = principal
                * (annualRatePercent / 100.0)
                * (months / 12.0);

        double total = principal + interest;

        String result = String.format(
                java.util.Locale.ROOT,
                "Principal: EUR %.2f; Simple interest: EUR %.2f; Total: EUR %.2f",
                principal, interest, total
        );

        log.info("Tool result: {}", result);

        return result;
    }
}
