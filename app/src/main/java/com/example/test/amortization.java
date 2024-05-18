package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;

public class amortization extends AppCompatActivity {
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

    FloatingActionButton fabMainAmortization, homeFabAmortization, calFabAmortization, emailFabAmortization;

    Boolean isAllFabsVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amortization);

        fabMainAmortization = findViewById(R.id.fabMainAmortization);
        homeFabAmortization = findViewById(R.id.homeFabAmortization);
        calFabAmortization = findViewById(R.id.calFabAmortization);
        emailFabAmortization = findViewById(R.id.emailFabAmortization);

        homeFabAmortization.setVisibility(View.GONE);
        calFabAmortization.setVisibility(View.GONE);
        emailFabAmortization.setVisibility(View.GONE);

        isAllFabsVisible = false;
        fabMainAmortization.show();
        fabMainAmortization.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!isAllFabsVisible) {

                            homeFabAmortization.show();
                            calFabAmortization.show();
                            emailFabAmortization.show();

                            fabMainAmortization.isExpanded();

                            isAllFabsVisible = true;
                        } else {

                            homeFabAmortization.hide();
                            calFabAmortization.hide();
                            emailFabAmortization.hide();

                            fabMainAmortization.show();

                            isAllFabsVisible = false;
                        }
                    }
                });
        // FAB - go back to main page (front)
        homeFabAmortization.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homePage = new Intent(amortization.this, front.class);
                        startActivity(homePage);
                    }
                }
        );
        // FAB - go back to calculator page (main)
        calFabAmortization.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent homePage = new Intent(amortization.this, MainActivity.class);
                        startActivity(homePage);
                    }
                }
        );


        Intent amortizationPage = getIntent();
        //String amortizationMonthlyPayment = amortizationPage.getStringExtra("key1");
        Double amortizationInterestRate = amortizationPage.getDoubleExtra("key2", 0.0);
        int amortizationLoanTerm = amortizationPage.getIntExtra("key3", 0);
        Double amortizationTotalLoanAmount = amortizationPage.getDoubleExtra("key4",0.0);
        Double amortizationDownPayment = amortizationPage.getDoubleExtra("key5", 0.0);
/*
        if (amortizationInterestRate == null){
            Log.e("MainActivity", "amortizationInterestRate is null");
        }
        double interestRateA = Double.parseDouble(amortizationInterestRate);
        int loanTermA = Integer.parseInt(String.valueOf(amortizationLoanTerm));
        double loanAmountA = Double.parseDouble(amortizationTotalLoanAmount);
        double downPaymentA = Double.parseDouble(amortizationDownPayment);

*/
        double actualLoanAmount = amortizationTotalLoanAmount - amortizationDownPayment;
        double annualInterest = amortizationInterestRate / 1200;
        double monthlyInterestRate = annualInterest / 1200;
        double monthlyPayA = actualLoanAmount * monthlyInterestRate/ (1 - Math.pow(1 + monthlyInterestRate, - amortizationLoanTerm* 12));
        double principal = monthlyPayA - (actualLoanAmount * annualInterest);
        double balance = principal;
        double currentInterest = 0;
        double totalInterest = 0;
        TableLayout tableBodyAmortization = findViewById(R.id.tableBodyAmortization);

        for (int i = 0; i < amortizationLoanTerm; i++) {

            currentInterest = monthlyInterestRate * actualLoanAmount; //finds the current interest due for that month
            totalInterest += currentInterest;
            principal = monthlyPayA - currentInterest; //calculates the principle (paid on loan) after the interest is deducted
            balance = Math.abs(balance - principal);

            double monthlyPayment = monthlyPayA;

            TableRow tableRowAmortization = new TableRow(this);
            tableRowAmortization.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView monthView = new TextView(this);
            monthView.setText(String.format("Month %d", i + 1));
            monthView.setPadding(75,30, 30, 30);
            monthView.setTextSize(14);

            TextView monthlyPaymentView = new TextView(this);
            monthlyPaymentView.setText(currencyFormat.format(monthlyPayment));
            monthlyPaymentView.setPadding(10, 10, 10, 10);
            monthlyPaymentView.setTextSize(14);

            TextView principalView = new TextView(this);
            principalView.setText(currencyFormat.format(principal));
            principalView.setPadding(10, 10, 10, 10);
            principalView.setTextSize(14);

            actualLoanAmount -= principal;

            TextView interestView = new TextView(this);
            interestView.setText(currencyFormat.format(currentInterest));
            interestView.setPadding(30, 10, 10, 10);
            interestView.setTextSize(14);

            TextView balanceView = new TextView(this);
            balanceView.setText(currencyFormat.format( actualLoanAmount));
            balanceView.setPadding(50, 10, 10, 10);
            balanceView.setTextSize(14);
            /*
            TextView currentInterestView = new TextView(this);
            balanceView.setText(currencyFormat.format(currentInterest));
            balanceView.setPadding(10,10, 10, 10);
            balanceView.setTextSize(14);

            TextView totalInterestView = new TextView(this);
            balanceView.setText(currencyFormat.format(totalInterest));
            balanceView.setPadding(10,10, 10, 10);
            balanceView.setTextSize(14);*/

            tableRowAmortization.addView(monthView);
            tableRowAmortization.addView(monthlyPaymentView);
            tableRowAmortization.addView(principalView);
            tableRowAmortization.addView(interestView);
            tableRowAmortization.addView(balanceView);
            //tableRowAmortization.addView(currentInterestView);
            //tableRowAmortization.addView(totalInterestView);


            tableBodyAmortization.addView(tableRowAmortization);
            //actualLoanAmount -= (monthlyPayA - (actualLoanAmount * amortizationInterestRate));
        }

    }
}
