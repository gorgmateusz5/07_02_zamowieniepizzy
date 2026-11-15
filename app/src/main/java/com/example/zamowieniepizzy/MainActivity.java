package com.example.zamowieniepizzy;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.materialswitch.MaterialSwitch;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgSize;

    private CheckBox cbCheese, cbHam, cbMushrooms, cbTerms;

    private SeekBar sbQuantity;
    private TextView tvQuantityValue, tvSummary;

    private Spinner spDough;
    private EditText etAddress;

    private MaterialSwitch swDelivery;

    private Button btnOrder, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rgSize = findViewById(R.id.rgSize);

        cbCheese = findViewById(R.id.cbCheese);
        cbHam = findViewById(R.id.cbHam);
        cbMushrooms = findViewById(R.id.cbMushrooms);
        cbTerms = findViewById(R.id.cbTerms);

        sbQuantity = findViewById(R.id.sbQuantity);
        tvQuantityValue = findViewById(R.id.tvQuantityValue);
        tvSummary = findViewById(R.id.tvSummary);

        spDough = findViewById(R.id.spDough);
        etAddress = findViewById(R.id.etAddress);

        swDelivery = findViewById(R.id.swDelivery);

        btnOrder = findViewById(R.id.btnOrder);
        btnClear = findViewById(R.id.btnClear);

        setUpSpinner();
        setUpListeners();
    }

    private void setUpSpinner() {

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.dough_types,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spDough.setAdapter(adapter);
    }

    private void setUpListeners() {

        cbTerms.setOnCheckedChangeListener(
                (button, checked) -> btnOrder.setEnabled(checked)
        );

        swDelivery.setOnCheckedChangeListener(
                (button, checked) -> {
                    etAddress.setEnabled(checked);

                    if (!checked) {
                        etAddress.setText("");
                        etAddress.setError(null);
                    }
                }
        );

        etAddress.setEnabled(false);

        sbQuantity.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar,
                            int progress,
                            boolean fromUser) {

                        tvQuantityValue.setText(
                                String.valueOf(progress + 1)
                        );
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });
    }
}