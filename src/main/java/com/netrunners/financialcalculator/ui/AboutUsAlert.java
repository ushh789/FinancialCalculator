package com.netrunners.financialcalculator.ui;

import com.netrunners.financialcalculator.constants.FileConstants;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AboutUsAlert {
    public static void showAboutUs(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About our application");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(FileConstants.LOGO));
        alert.setHeaderText(null);
        alert.setContentText("""
                    Title: Financial Calculator

                    Description:
                    The "Financial Calculator" is a versatile and user-friendly software application designed to help individuals and businesses make informed financial decisions by calculating both deposit and credit-related parameters. With a range of powerful features, this tool simplifies financial planning and provides users with valuable insights into their financial investments and liabilities.

                    Key Features:

                    Deposit Calculator:

                    Calculate Compound Interest: Determine the future value of your savings or investments with compound interest calculations.
                    Flexible Periods: Specify the deposit term in months or years to align with your financial goals.
                    Customizable Contributions: Account for regular deposits or contributions to your savings.
                    Interest Rate Options: Calculate interest with both fixed and variable interest rates.

                    Credit Calculator:

                    Loan Repayment Estimation: Calculate the monthly or periodic payments required to pay off a loan or credit.
                    Amortization Schedules: View detailed amortization schedules to track the progress of your loan repayment.
                    Interest Considerations: Analyze how interest rates impact the total cost of a loan.
                    Interactive Graphs: Visualize loan repayment and interest payments through user-friendly graphs.

                    Dark and Light Themes:

                    Choose between dark and light themes to customize the application's appearance and enhance user experience.

                    "Financial Calculator" empowers individuals, students, professionals, and business owners to make informed decisions about their financial affairs. Whether you're planning to save money or seeking insights into loan repayments, this versatile tool offers a comprehensive set of features to help you on your financial journey. Start making smarter financial decisions today with this powerful yet user-friendly financial calculator.""");
        alert.showAndWait();
    }
}
