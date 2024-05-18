package com.example.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.Math;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private EditText loanAmountEditText;
    private EditText interestRateEditText;
    private EditText downAmountEditText;
    private EditText yearsEditText;
    private TextView resultTextView;
    private TextView yearPaymentDollar;
    private TextView totalOverallPayment;
    private TextView addMonthlyPaymentDollar;
    private EditText addYearlyPaymentDollar;
    private TextView numberOfPayments;
    private TextView totalSavingsInterest;
    private TextView totalInterest;

    FloatingActionButton fabBean1, fabHome, fabSettings,fabZillow, fabAmortization;
    Boolean isAllFabsVisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loanAmountEditText = findViewById(R.id.loanAmountEditText);
        interestRateEditText = findViewById(R.id.interestRateEditText);
        yearsEditText = findViewById(R.id.yearsEditText);
        downAmountEditText = findViewById(R.id.downAmountEditText);


        resultTextView = findViewById(R.id.resultTextView);
        totalInterest = findViewById(R.id.totalInterest);
        yearPaymentDollar = findViewById(R.id.yearPaymentDollar);

        addMonthlyPaymentDollar = findViewById(R.id.addMonthlyPaymentDollar);
        addYearlyPaymentDollar = findViewById(R.id.addYearlyPaymentDollar);
        numberOfPayments = findViewById(R.id.numberOfPayments);
        totalSavingsInterest = findViewById(R.id.totalSavingsInterest);
        totalOverallPayment = findViewById(R.id.totalOverallPayment);

        //https://www.geeksforgeeks.org/extended-floating-action-button-in-android-with-example/
        fabBean1 = findViewById(R.id.fabBean1);
        fabHome = findViewById(R.id.fabHome);
        fabSettings = findViewById(R.id.fabSettings);
        fabZillow = findViewById(R.id.fabZillow);
        fabAmortization = findViewById(R.id.fabAmortization);

        fabHome.setVisibility(View.GONE);
        fabSettings.setVisibility(View.GONE);
        fabZillow.setVisibility(View.GONE);
        fabAmortization.setVisibility(View.GONE);

        isAllFabsVisible = false;

        fabBean1.show();
        fabBean1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            fabHome.show();
                            fabSettings.show();
                            fabZillow.show();
                            fabAmortization.show();

                            fabBean1.isExpanded();

                            isAllFabsVisible = true;
                        } else {

                            fabHome.hide();
                            fabSettings.hide();
                            fabZillow.hide();
                            fabAmortization.hide();

                            fabBean1.show();

                            isAllFabsVisible = false;
                        }
                    }
                });
        // FAB - go back to main page (front)
        fabHome.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homePage = new Intent(MainActivity.this, front.class);
                        startActivity(homePage);
                    }
                }
        );
        //FAB - go to settings page
        fabSettings.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent settingsPage = new Intent(MainActivity.this, settings.class);
                        startActivity(settingsPage);
                    }
                }
        );
        //FAB to go to Zillow webpage - unsure if it will work tho
        fabZillow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String urlZillow = "https://www.zillow.com/";
                        Intent zillowWebPage = new Intent(Intent.ACTION_VIEW);
                        zillowWebPage.setData(Uri.parse(urlZillow));
                        startActivity(zillowWebPage);
                    }
                }
        );
        Button calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                double loanAmount = Double.parseDouble(loanAmountEditText.getText().toString());
                double interestRate = Double.parseDouble(interestRateEditText.getText().toString());
                double monthlyInterestRate = interestRate/1200;
                double downPaymentF = Double.parseDouble(downAmountEditText.getText().toString());
                double totalLoanAmount = loanAmount - downPaymentF;
                int loanTerm = Integer.parseInt(yearsEditText.getText().toString());

                //Monthly Payment
                double monthlyPayment = totalLoanAmount * monthlyInterestRate/ (1 - Math.pow(1 + monthlyInterestRate, - loanTerm* 12));
                resultTextView.setText(currencyFormat.format(monthlyPayment));

                //Yearly Payment
                double yearlyPayment = monthlyPayment * 12;
                yearPaymentDollar.setText(currencyFormat.format(yearlyPayment));

                //Total Interest
                double interestCalculated = (((monthlyPayment * loanTerm) - totalLoanAmount));
                double totalOverallInterest = Math.abs(interestCalculated);
                totalInterest.setText(currencyFormat.format(totalOverallInterest));

                //total overall cost
                double overallPayment = totalOverallInterest + totalLoanAmount;
                totalOverallPayment.setText(currencyFormat.format(overallPayment));

            }
        });

        Button calculateButton2 = findViewById(R.id.calculateButton2);

        calculateButton2.setOnClickListener(new View.OnClickListener(){
            private double reformatCurrency(String currency) {
                return Double.parseDouble(currency.replaceAll("[^\\d.]", ""));
            }
            @Override
            public void onClick (View v) {
/*                 //reformatting remove the currency format
                String formattedMonthlyAmount = resultTextView.getText().toString();
                String monthlyPaymentOnly = formattedMonthlyAmount.replaceAll("[^\\d.]", "");
                String formattedInterest = totalInterest.getText().toString();
                String interestOnly = formattedInterest.replaceAll("[^\\d.]", "");

                double loanAmount1 = Double.parseDouble(loanAmountEditText.getText().toString());
                double monthlyPayment1 = Double.parseDouble(monthlyPaymentOnly);
                double interestRate1 = Double.parseDouble(interestRateEditText.getText().toString());
                double totalOverallInterest = Double.parseDouble(interestOnly);
                double downPaymentF1 = Double.parseDouble(downAmountEditText.getText().toString());
                //double totalInterestSavings = Double.parseDouble(totalSavingsInterest.getText().toString());
                double totalLoanAmount1 = loanAmount1 - downPaymentF1;
                int loanTerm1 = Integer.parseInt(yearsEditText.getText().toString());
 */
                double loanAmount = reformatCurrency(loanAmountEditText.getText().toString());
                double monthlyPayment = reformatCurrency(resultTextView.getText().toString());
                double interestRate = reformatCurrency(interestRateEditText.getText().toString());
                double interestOnly = reformatCurrency(totalInterest.getText().toString());
                //double downPayment = reformatCurrency(downAmountEditText.getText().toString());
                //int loanTerm = Integer.parseInt(yearsEditText.getText().toString());


                //additional monthly payment and yearly payment
                double additionalMonthlyPayment;
                if (addMonthlyPaymentDollar.getText().toString().isEmpty()) {
                    additionalMonthlyPayment = 0;
                } else {
                    additionalMonthlyPayment = Double.parseDouble(addMonthlyPaymentDollar.getText().toString());
                }
                double additionalYearlyPayment;
                if (addYearlyPaymentDollar.getText().toString().isEmpty()) {
                    additionalYearlyPayment = 0;
                } else {
                    additionalYearlyPayment = Double.parseDouble(addYearlyPaymentDollar.getText().toString());
                }

                //double totalLoanAmount = loanAmount - downPayment;
                //double newInterest = interestOnly;
                //int remainingLoanTerm = loanTerm;
                double additionalPayment = additionalMonthlyPayment + additionalYearlyPayment / 12;

/*
                while (newLoanAmount > 0) {
                    newLoanAmount = newLoanAmount * (1 + interestRate1) - additionalPaymentPerMonth;
                    totalInterestSavings = totalOverallInterest - (monthlyPayment + additionalPaymentPerMonth - (newLoanAmount * interestRate1));
                    newLoanTerm--;
                }
                //numberOfPayments.setText(currencyFormat.format(loanTerm1 - newLoanTerm));
                //totalSavingsInterest.setText(currencyFormat.format(totalOverallInterest - newTotalInterest));
                //additional yearly payment


                newLoanTerm = loanTerm1;
                newLoanAmount = totalLoanAmount1;

                while (newLoanAmount > 0) {
                    newLoanAmount = newLoanAmount * (1 + interestRate1) - (additionalPaymentPerYear / 12);
                    totalInterestSavings = totalOverallInterest - (monthlyPayment + (additionalPaymentPerYear / 12) - (newLoanAmount * interestRate1));
                    newLoanTerm--;
                }
 */
                //additional Monthly and/or Yearly Payments
                double newMonthlyPayment = monthlyPayment + additionalPayment;
                int newLoanTerm = (int) Math.ceil(loanAmount * (1 + (interestRate/100)) / newMonthlyPayment);
                numberOfPayments.setText(String.valueOf(newLoanTerm));

                //total savings with additional payments
                double newTotalInterest = ((newMonthlyPayment * newLoanTerm) - loanAmount) * -1;
                double totalSavings = interestOnly - newTotalInterest;
                totalSavingsInterest.setText(currencyFormat.format(totalSavings));
            }

        });
        fabAmortization.setOnClickListener(
                new View.OnClickListener() {

                    private double reformatCurrency2(String currency) {
                        return Double.parseDouble(currency.replaceAll("[^\\d.]", ""));
                    }

                    @Override
                    public void onClick(View v) {
                        //double monthlyPaymentA = reformatCurrency2(resultTextView.getText().toString());
                        Intent amortizationPage = new Intent(MainActivity.this, amortization.class);
                        //loan details for amortization

                        double loanAmountA = Double.parseDouble(loanAmountEditText.getText().toString());
                        double downPaymentA = Double.parseDouble(downAmountEditText.getText().toString());
                        double interestRateAmortization = Double.parseDouble(interestRateEditText.getText().toString());
                        int loanTermAmortization = Integer.parseInt(yearsEditText.getText().toString());
                        //double monthlyPaymentAmortization =Double.parseDouble(resultTextView.getText().toString());0/*
                       /* double loanAmountA = 0;
                        String loanAmountString = loanAmountEditText.getText().toString();
                        if (!loanAmountString.isEmpty()) {
                            loanAmountA = Double.parseDouble(loanAmountString);
                        } else{
                            Toast.makeText(MainActivity.this, "Loan Amount field cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        double downPaymentA = 0;
                        String downAmountString = downAmountEditText.getText().toString();
                        if (!downAmountString.isEmpty()) {
                            downPaymentA = Double.parseDouble(downAmountString);
                        }else{
                            Toast.makeText(MainActivity.this, "Down Payment Amount field cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                        double interestRateAmortization = 0;
                        String interestRateString = interestRateEditText.getText().toString();
                        if (!interestRateString.isEmpty()) {
                            interestRateAmortization = Double.parseDouble(interestRateString);
                        }else{
                            Toast.makeText(MainActivity.this, "Interest Rate field cannot be empty", Toast.LENGTH_SHORT).show();
                        }

                        double interestRateAmortization = Double.parseDouble(interestRateEditText.getText().toString());

                        int loanTermAmortization = 0;
                        String loanTermString = yearsEditText.getText().toString();
                        if (!loanTermString.isEmpty()) {
                            loanTermAmortization = Integer.parseInt(loanTermString);
                        }else{
                            Toast.makeText(MainActivity.this, "Loan Term field cannot be empty", Toast.LENGTH_SHORT).show();
                        }*/

                        /*double monthlyPaymentAmortization;
                        if (monthlyPaymentA != 0) {
                            monthlyPaymentAmortization = monthlyPaymentA;
                        } else {
                            monthlyPaymentAmortization = 0;
                            Toast.makeText(MainActivity.this, "Monthly Payment field cannot be empty", Toast.LENGTH_SHORT).show();
                        }*/


                        //values to grab
                        //amortizationPage.putExtra("key1", monthlyPaymentAmortization);
                        amortizationPage.putExtra("key2", interestRateAmortization);
                        amortizationPage.putExtra("key3", loanTermAmortization);
                        amortizationPage.putExtra("key4", loanAmountA);
                        amortizationPage.putExtra("key5", downPaymentA);
                        startActivity(amortizationPage);
                    }
                }
        );
    }

}