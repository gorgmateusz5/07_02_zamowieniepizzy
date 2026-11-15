package com.example.zamowieniepizzy;
import androidx.appcompat.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.materialswitch.MaterialSwitch;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private RadioGroup rgSize;
    private FormState previousState;
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

    private static class FormState {

        String address;
        int size;
        int dough;
        int quantity;

        boolean cheese;
        boolean ham;
        boolean mushrooms;
        boolean terms;
        boolean delivery;
    }
    private void restoreFormState(FormState state) {

        swDelivery.setChecked(state.delivery);

        etAddress.setText(state.address);
        etAddress.setEnabled(state.delivery);
        etAddress.setError(null);

        if (state.size == -1) {
            rgSize.clearCheck();
        } else {
            rgSize.check(state.size);
        }

        spDough.setSelection(state.dough);

        sbQuantity.setProgress(state.quantity);

        cbCheese.setChecked(state.cheese);
        cbHam.setChecked(state.ham);
        cbMushrooms.setChecked(state.mushrooms);
        cbTerms.setChecked(state.terms);

        tvSummary.setText("");
    }
    private FormState saveFormState() {

        FormState state = new FormState();

        state.address = etAddress.getText().toString();
        state.size = rgSize.getCheckedRadioButtonId();
        state.dough = spDough.getSelectedItemPosition();
        state.quantity = sbQuantity.getProgress();

        state.cheese = cbCheese.isChecked();
        state.ham = cbHam.isChecked();
        state.mushrooms = cbMushrooms.isChecked();
        state.terms = cbTerms.isChecked();
        state.delivery = swDelivery.isChecked();

        return state;
    }
    private boolean validateForm() {

        if (rgSize.getCheckedRadioButtonId() == -1) {
            Toast.makeText(
                    this,
                    R.string.error_size,
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }

        if (spDough.getSelectedItemPosition() == 0) {
            Toast.makeText(
                    this,
                    R.string.error_dough,
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }

        if (swDelivery.isChecked()
                && etAddress.getText().toString().trim().isEmpty()) {

            etAddress.setError(
                    getString(R.string.error_address)
            );

            etAddress.requestFocus();

            return false;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(
                    this,
                    R.string.error_terms,
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }

        return true;
    }

    private String getSize() {

        int id = rgSize.getCheckedRadioButtonId();

        if (id == R.id.rbSmall) {
            return getString(R.string.small);
        }

        if (id == R.id.rbMedium) {
            return getString(R.string.medium);
        }

        return getString(R.string.large);
    }

    private String getToppings() {

        String result = "";

        if (cbCheese.isChecked()) {
            result += getString(R.string.cheese) + ", ";
        }

        if (cbHam.isChecked()) {
            result += getString(R.string.ham) + ", ";
        }

        if (cbMushrooms.isChecked()) {
            result += getString(R.string.mushrooms) + ", ";
        }

        if (result.isEmpty()) {
            return "Brak";
        }

        return result.substring(0, result.length() - 2);
    }

    private String buildSummary() {

        String delivery = swDelivery.isChecked()
                ? "Tak"
                : "Odbiór osobisty";

        String address = swDelivery.isChecked()
                ? etAddress.getText().toString().trim()
                : "Brak";

        return getString(
                R.string.summary_format,
                getSize(),
                getToppings(),
                sbQuantity.getProgress() + 1,
                spDough.getSelectedItem().toString(),
                delivery,
                address
        );
    }

    private void askForConfirmation() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(R.string.confirm_message)
                .setPositiveButton(
                        R.string.yes,
                        (dialog, which) -> submitOrder()
                )
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void submitOrder() {

        tvSummary.setText(buildSummary());

        Snackbar.make(
                findViewById(R.id.main),
                R.string.ordered,
                Snackbar.LENGTH_LONG
        ).show();
    }
    private void askForClearConfirmation() {

        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_title)
                .setMessage(R.string.clear_message)
                .setPositiveButton(
                        R.string.yes,
                        (dialog, which) -> clearForm()
                )
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void clearForm() {

        previousState = saveFormState();

        etAddress.setText("");
        etAddress.setError(null);

        rgSize.clearCheck();

        spDough.setSelection(0);

        sbQuantity.setProgress(0);

        cbCheese.setChecked(false);
        cbHam.setChecked(false);
        cbMushrooms.setChecked(false);
        cbTerms.setChecked(false);

        swDelivery.setChecked(false);

        tvSummary.setText("");

        Snackbar.make(
                        findViewById(R.id.main),
                        R.string.cleared,
                        Snackbar.LENGTH_LONG
                )
                .setAction(R.string.undo, v -> {

                    if (previousState != null) {
                        restoreFormState(previousState);
                    }

                })
                .show();
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
        btnClear.setOnClickListener(v -> askForClearConfirmation());
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
        btnOrder.setOnClickListener(v -> {

            if (validateForm()) {
                askForConfirmation();
            }

        });
    }
}