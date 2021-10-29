package com.kozikov.crs.web.screens.minymalpolynomial;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.screen.*;
import com.kozikov.crs.entity.LRP;
import com.kozikov.crs.entity.RecurrentRelation;
import com.kozikov.crs.service.ConstantRecursiveSequenceService;
import org.jsoup.internal.StringUtil;

import javax.inject.Inject;
import java.util.List;

@UiController("crs_MinimalPolynomial")
@UiDescriptor("minimal-polynomial.xml")
public class MinimalPolynomial extends Screen {

    @Inject
    private TextField<String> moduleField;
    @Inject
    private TextField<String> initialVectorField;
    @Inject
    private TextField<String> relationField;
    @Inject
    private Button calculateBtn;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private ConstantRecursiveSequenceService constantRecursiveSequenceService;
    @Inject
    private TextField<String> characteristicPolynomialField;
    @Inject
    private TextField<String> generatorField;
    @Inject
    private TextField<String> sequenceField;
    @Inject
    private TextField<String> minimalPolynomialField;
    @Inject
    private TextField<String> recurrentRelationField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        calculateBtn.setEnabled(false);
    }

    @Subscribe("moduleField")
    public void onModuleFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        if (event.getValue() != null) {
            if (!StringUtil.isNumeric(event.getValue())) {
                notifications.create(Notifications.NotificationType.TRAY)
                        .withCaption(messageBundle.getMessage("numberModuleError"))
                        .show();
                moduleField.clear();
            }
        }
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("relationField")
    public void onRelationFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("initialVectorField")
    public void onInitialVectorFieldValueChange(HasValue.ValueChangeEvent<String> event) {
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    @Subscribe("calculateBtn")
    public void onCalculateBtnClick(Button.ClickEvent event) {
        String modF = moduleField.getValue();
        String recurrentRelationStringArray = relationField.getValue();
        if (recurrentRelationStringArray != null && modF != null && initialVectorField.getValue() != null) {
            RecurrentRelation recurrentRelation = constantRecursiveSequenceService
                    .getRelationFromStringArray(recurrentRelationStringArray.split(" "), Integer.parseInt(modF));
            recurrentRelationField.setValue(recurrentRelation.toString());
            List<Integer> initialVector = constantRecursiveSequenceService
                    .getVectorFromStringArray(initialVectorField.getValue().split(" "));
            LRP lrp = new LRP(recurrentRelation, initialVector);
            sequenceField.setValue(lrp.getSequence(20).toString()
                    .replace("[", "").replace("]", "").replaceAll(",", ""));
            characteristicPolynomialField.setValue(lrp.getCharacteristicPolynomial().toString());
            generatorField.setValue(lrp.getGenerator().toString());
            minimalPolynomialField.setValue(lrp.getMinimalPolynomial().toString());
        }
        calculateBtn.setEnabled(isCalculatingAvailable());
    }

    private boolean isCalculatingAvailable() {
        return initialVectorField.getValue() != null &&
                moduleField.getValue() != null && relationField.getValue() != null;
    }
}