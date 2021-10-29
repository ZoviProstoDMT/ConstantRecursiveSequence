package com.kozikov.crs.web.screens.polynomialoperations;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.screen.*;
import com.kozikov.crs.entity.ArithmeticOperations;
import com.kozikov.crs.entity.Polynomial;
import com.kozikov.crs.service.PolynomialService;
import org.jsoup.internal.StringUtil;

import javax.inject.Inject;
import java.util.Objects;

@UiController("crs_PolynomialsOperations")
@UiDescriptor("polynomials-operations.xml")
public class PolynomialsOperations extends Screen {

    @Inject
    private Label<String> polynomialOneLabel;
    @Inject
    private Label<String> polynomialTwoLabel;
    @Inject
    private PolynomialService polynomialService;
    @Inject
    private TextField<String> polynomialOneField;
    @Inject
    private TextField<String> polynomialTwoField;
    @Inject
    private Label<String> resultLabel;
    @Inject
    private Label<String> operationLabel;
    @Inject
    private Button calculateBtn;
    @Inject
    private TextField<String> moduleField;
    @Inject
    private LookupField<ArithmeticOperations> operationField;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        calculateBtn.setEnabled(false);
        polynomialOneField.setEditable(false);
        polynomialTwoField.setEditable(false);
    }

    @Subscribe("polynomialOneField")
    public void onPolynomialOneFieldTextChange(TextInputField.TextChangeEvent event) {
        resultLabel.setValue("");
        Polynomial polynomial = polynomialService.getFromStringArray(event.getText().split(" "),
                Integer.parseInt(Objects.requireNonNull(moduleField.getValue())));
        polynomialOneLabel.setValue(String.valueOf(polynomial));
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("polynomialTwoField")
    public void onPolynomialTwoFieldTextChange(TextInputField.TextChangeEvent event) {
        resultLabel.setValue("");
        Polynomial polynomial = polynomialService.getFromStringArray(event.getText().split(" "),
                Integer.parseInt(Objects.requireNonNull(moduleField.getValue())));
        polynomialTwoLabel.setValue(String.valueOf(polynomial));
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("moduleField")
    public void onModuleFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        resultLabel.setValue("");
        polynomialOneField.setEditable(false);
        polynomialTwoField.setEditable(false);
        if (event.getValue() != null) {
            if (StringUtil.isNumeric(event.getValue())) {
                polynomialOneField.setEditable(true);
                polynomialTwoField.setEditable(true);
            } else {
                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption(messageBundle.getMessage("numberModuleError"))
                        .show();
                moduleField.clear();
            }
        }
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("operationField")
    public void onOperationFieldValueChange(HasValue.ValueChangeEvent<ArithmeticOperations> event) {
        resultLabel.setValue("");
        if (event.getValue() != null) {
            operationLabel.setValue(Objects.requireNonNull(operationField.getValue()).getId());
        }
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("calculateBtn")
    public void onCalculateBtnClick(Button.ClickEvent event) {
        if (moduleField.getValue() != null) {
            if (polynomialOneField.getValue() != null && polynomialTwoField.getValue() != null) {
                Polynomial one = polynomialService.getFromStringArray(polynomialOneField.getValue().split(" "),
                        Integer.parseInt(moduleField.getValue()));
                Polynomial two = polynomialService.getFromStringArray(polynomialTwoField.getValue().split(" "),
                        Integer.parseInt(moduleField.getValue()));
                if (operationField.getValue() != null) {
                    switch (operationField.getValue()) {
                        case SUM:
                            resultLabel.setValue(String.valueOf(one.sum(two)));
                            break;
                        case SUBTRACT:
                            resultLabel.setValue(String.valueOf(one.subtract(two)));
                            break;
                        case MULTIPLY:
                            resultLabel.setValue(String.valueOf(one.multiply(two)));
                            break;
                        case DIVIDE:
                            resultLabel.setValue(String.valueOf(one.divide(two)));
                            break;
                    }
                }
            }
        }
    }

    private boolean isCalculatingAvailable() {
        return polynomialOneField.getValue() != null && polynomialTwoField.getValue() != null &&
                moduleField.getValue() != null && operationField.getValue() != null;
    }
}