package org.overengineer.inlineproblems.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.ColorPanel;
import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import lombok.Getter;
import org.overengineer.inlineproblems.DocumentMarkupModelScanner;
import org.overengineer.inlineproblems.listeners.HighlightProblemListener;
import org.overengineer.inlineproblems.listeners.MarkupModelProblemListener;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;


public class SettingsComponent {
    private final JBCheckBox showErrors = new JBCheckBox("Show errors");
    private final JBCheckBox highlightErrors = new JBCheckBox("Highlight errors");
    private final JBCheckBox showWarnings = new JBCheckBox("Show warnings");
    private final JBCheckBox highlightWarnings = new JBCheckBox("Highlight warnings");
    private final JBCheckBox showWeakWarnings = new JBCheckBox("Show weak warnings");
    private final JBCheckBox highlightWeakWarnings = new JBCheckBox("Highlight weak warnings");
    private final JBCheckBox showInfos = new JBCheckBox("Show infos");
    private final JBCheckBox highlightInfo = new JBCheckBox("Highlight infos");
    private final ColorPanel errorTextColor = new ColorPanel();
    private final ColorPanel errorLabelBackgroundColor = new ColorPanel();
    private final ColorPanel errorHighlightColor = new ColorPanel();
    private final ColorPanel warningTextColor = new ColorPanel();
    private final ColorPanel warningLabelBackgroundColor = new ColorPanel();
    private final ColorPanel warningHighlightColor = new ColorPanel();
    private final ColorPanel weakWarningTextColor = new ColorPanel();
    private final ColorPanel weakWarningLabelBackgroundColor = new ColorPanel();
    private final ColorPanel weakWarningHighlightColor = new ColorPanel();
    private final ColorPanel infoTextColor = new ColorPanel();
    private final ColorPanel infoLabelBackgroundColor = new ColorPanel();
    private final ColorPanel infoHighlightColor = new ColorPanel();
    private final JBCheckBox forceErrorsInSameLine = new JBCheckBox("Force problems in the same line even if they are to long to fit");
    private final JBCheckBox drawBoxesAroundProblemLabels = new JBCheckBox("Draw boxes around problem labels");
    private final JBCheckBox roundedCornerBoxes = new JBCheckBox("Rounded corners");
    private final JBCheckBox useEditorFont = new JBCheckBox("Use editor font instead of tooltip font");

    private final JBCheckBox showOnlyHighestSeverityPerLine = new JBCheckBox("Show only the problem with the highest severity per line");
    private final JFormattedTextField inlayFontSizeDeltaText;
    private final JFormattedTextField manualScannerDelay;
    private final JBCheckBox fillProblemLabels = new JBCheckBox("Fill problem label background");
    private final JBCheckBox boldProblemLabels = new JBCheckBox("Bold problem labels");
    private final JBCheckBox italicProblemLabels = new JBCheckBox("Italic problem labels");
    private final JBTextField problemFilterList = new JBTextField("Problem text beginning filter");

    private final String[] availableListeners = {HighlightProblemListener.NAME, MarkupModelProblemListener.NAME, DocumentMarkupModelScanner.NAME};
    private final JComboBox<String> enabledListener = new ComboBox<>(availableListeners);

    private final JBTextField additionalInfoSeverities = new JBTextField();
    private final JBTextField additionalWarningSeverities = new JBTextField();
    private final JBTextField additionalWeakWarningSeverities = new JBTextField();
    private  final JBTextField additionalErrorSeverities = new JBTextField();

    @Getter
    private final JPanel settingsPanel;

    public SettingsComponent() {
        SettingsState settingsState = SettingsState.getInstance();

        showErrors.setSelected(settingsState.isShowErrors());
        highlightErrors.setSelected(settingsState.isHighlightErrors());
        errorTextColor.setSelectedColor(settingsState.getErrorTextColor());
        errorLabelBackgroundColor.setSelectedColor(settingsState.getErrorBackgroundColor());
        errorHighlightColor.setSelectedColor(settingsState.getErrorHighlightColor());

        showWarnings.setSelected(settingsState.isShowWarnings());
        highlightWarnings.setSelected(settingsState.isHighlightWarnings());
        warningTextColor.setSelectedColor(settingsState.getWarningTextColor());
        warningLabelBackgroundColor.setSelectedColor(settingsState.getWarningBackgroundColor());
        warningHighlightColor.setSelectedColor(settingsState.getWarningHighlightColor());

        showWeakWarnings.setSelected(settingsState.isShowWeakWarnings());
        highlightWeakWarnings.setSelected(settingsState.isHighlightWeakWarnings());
        weakWarningTextColor.setSelectedColor(settingsState.getWeakWarningTextColor());
        weakWarningLabelBackgroundColor.setSelectedColor(settingsState.getWeakWarningBackgroundColor());
        weakWarningHighlightColor.setSelectedColor(settingsState.getWeakWarningHighlightColor());

        showInfos.setSelected(settingsState.isShowInfos());
        highlightInfo.setSelected(settingsState.isHighlightInfos());
        infoTextColor.setSelectedColor(settingsState.getInfoTextColor());
        infoLabelBackgroundColor.setSelectedColor(settingsState.getInfoBackgroundColor());
        infoHighlightColor.setSelectedColor(settingsState.getInfoHighlightColor());

        forceErrorsInSameLine.setSelected(settingsState.isForceProblemsInSameLine());
        drawBoxesAroundProblemLabels.setSelected(settingsState.isDrawBoxesAroundErrorLabels());
        roundedCornerBoxes.setSelected(settingsState.isRoundedCornerBoxes());

        useEditorFont.setSelected(settingsState.isUseEditorFont());

        NumberFormat intFormat = NumberFormat.getIntegerInstance();
        intFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(intFormat);
        numberFormatter.setValueClass(Integer.class); // Optional, ensures we always get a int value

        inlayFontSizeDeltaText = new JFormattedTextField(numberFormatter);
        inlayFontSizeDeltaText.setText(Integer.toString(settingsState.getInlayFontSizeDelta()));

        manualScannerDelay = new JFormattedTextField(numberFormatter);
        manualScannerDelay.setText(Integer.toString(settingsState.getManualScannerDelay()));

        fillProblemLabels.setSelected(settingsState.isFillProblemLabels());
        boldProblemLabels.setSelected(settingsState.isBoldProblemLabels());
        italicProblemLabels.setSelected(settingsState.isItalicProblemLabels());

        additionalInfoSeverities.setText(settingsState.getAdditionalInfoSeveritiesAsString());
        additionalWeakWarningSeverities.setText(settingsState.getAdditionalWeakWarningSeveritiesAsString());
        additionalWarningSeverities.setText(settingsState.getAdditionalWarningSeveritiesAsString());
        additionalErrorSeverities.setText(settingsState.getAdditionalErrorSeveritiesAsString());

        problemFilterList.setText(settingsState.getProblemFilterList());
        enabledListener.setSelectedItem(Optional.of(settingsState.getEnabledListener()));

        Dimension enabledListenerDimension = enabledListener.getPreferredSize();
        enabledListenerDimension.width += 100;
        enabledListener.setPreferredSize(enabledListenerDimension);

        settingsPanel = FormBuilder.createFormBuilder()
                .addComponent(new JBLabel("Box / Label"))
                .addComponent(drawBoxesAroundProblemLabels, 0)
                .addComponent(roundedCornerBoxes, 0)
                .addComponent(fillProblemLabels, 0)
                .addComponent(boldProblemLabels, 0)
                .addComponent(italicProblemLabels, 0)
                .addSeparator()
                .addComponent(new JBLabel("General"))
                .addLabeledComponent(new JBLabel("Enabled problem listener"), enabledListener)
                .addTooltip("- MarkupModelListener (default): Called after addition of a RangeHighlighter to a file. Faster on large files, slower on small ones")
                .addTooltip("- HighlightProblemListener: Faster on small to medium sized files, slower on large ones. Called very often and can cause slowdowns.")
                .addTooltip("- ManualScanner: Scans the DocumentMarkupModel for all highlighters at a fixed delay, uses the same logic ")
                .addTooltip("   as HighlightProblemListener but can help with slowdowns on big files.")
                .addLabeledComponent(new JBLabel("ManualScanner delay in milliseconds"), manualScannerDelay)
                .addTooltip("Delay between manual scans, only used when ManualScanner is enabled")
                .addComponent(forceErrorsInSameLine, 0)
                .addComponent(useEditorFont, 0)
                .addComponent(showOnlyHighestSeverityPerLine, 0)
                .addLabeledComponent(new JBLabel("Inlay size delta"), inlayFontSizeDeltaText)
                .addTooltip("Used to have smaller font size for the inlays, should be smaller than editor font size")
                .addLabeledComponent(new JLabel("Problem filter list"), problemFilterList)
                .addTooltip("Semicolon separated list of problem text beginnings that will not be handled")
                .addSeparator()
                .addComponent(new JBLabel("Colors"))
                .addComponent(showErrors)
                .addComponent(highlightErrors)
                .addLabeledComponent(new JLabel("Error text color:"), errorTextColor)
                .addLabeledComponent(new JLabel("Error label border color:"), errorLabelBackgroundColor)
                .addLabeledComponent(new JLabel("Error line highlight color:"), errorHighlightColor)
                .addLabeledComponent(new JLabel("Additional severities:"), additionalErrorSeverities)
                .addTooltip("Semicolon separated list of additional error severities e.g. '10, 100'")
                .addSeparator()
                .addComponent(showWarnings)
                .addComponent(highlightWarnings)
                .addLabeledComponent(new JLabel("Warning text color:"), warningTextColor)
                .addLabeledComponent(new JLabel("Warning label border color:"), warningLabelBackgroundColor)
                .addLabeledComponent(new JLabel("Warning line highlight color:"), warningHighlightColor)
                .addLabeledComponent(new JLabel("Additional severities:"), additionalWarningSeverities)
                .addTooltip("Semicolon separated list of additional warning severities e.g. '10, 100'")
                .addSeparator()
                .addComponent(showWeakWarnings)
                .addComponent(highlightWeakWarnings)
                .addLabeledComponent(new JLabel("Weak warning text color:"), weakWarningTextColor)
                .addLabeledComponent(new JLabel("Weak warning label border color:"), weakWarningLabelBackgroundColor)
                .addLabeledComponent(new JLabel("Weak warning line highlight color:"), weakWarningHighlightColor)
                .addLabeledComponent(new JLabel("Additional severities:"), additionalWeakWarningSeverities)
                .addTooltip("Semicolon separated list of additional weak-warning severities e.g. '10, 100'")
                .addSeparator()
                .addComponent(showInfos)
                .addComponent(highlightInfo)
                .addLabeledComponent(new JLabel("Info text color:"), infoTextColor)
                .addLabeledComponent(new JLabel("Info label border color:"), infoLabelBackgroundColor)
                .addLabeledComponent(new JLabel("Info line highlight color:"), infoHighlightColor)
                .addLabeledComponent(new JLabel("Additional severities:"), additionalInfoSeverities)
                .addTooltip("Semicolon separated list of additional info severities e.g. '10, 100'")
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JComponent getPreferredFocusedComponent() {
        return settingsPanel;
    }

    public boolean isForceErrorsInSameLine() {
        return forceErrorsInSameLine.isSelected();
    }

    public void setForceErrorsInSameLine(final boolean isSelected) {
        forceErrorsInSameLine.setSelected(isSelected);
    }

    public boolean getDrawBoxesAroundProblemLabels() {
        return drawBoxesAroundProblemLabels.isSelected();
    }

    public void setDrawBoxesAroundProblemLabels(final boolean isSelected) {
        drawBoxesAroundProblemLabels.setSelected(isSelected);
    }

    public boolean isRoundedCornerBoxes() {
        return roundedCornerBoxes.isSelected();
    }

    public void setRoundedCornerBoxes(boolean isSelected) {
        roundedCornerBoxes.setSelected(isSelected);
    }

    public boolean isUseEditorFont() {
        return useEditorFont.isSelected();
    }

    public boolean isShowOnlyHighestSeverityPerLine() {
        return showOnlyHighestSeverityPerLine.isSelected();
    }

    public int getInlayFontSizeDelta() {
        int val = 0;
        // Convert the String
        try {
            val = Integer.parseInt(inlayFontSizeDeltaText.getText());
        }
        catch (NumberFormatException ignored) {}

        if (val < 0)
            val = 0;

        return val;
    }

    public void setInlayFontSizeDelta(int val) {
        inlayFontSizeDeltaText.setText(String.valueOf(Math.max(0, val)));
    }

    public void setUseEditorFont(boolean isSelected) {
        useEditorFont.setSelected(isSelected);
    }

    public void setShowOnlyHighestSeverityPerLine(boolean isSelected) {
        showOnlyHighestSeverityPerLine.setSelected(isSelected);
    }

    public boolean isFillProblemLabels() {
        return fillProblemLabels.isSelected();
    }

    public void setFillProblemLabels(boolean isSelected) {
        fillProblemLabels.setSelected(isSelected);
    }

    public boolean isBoldProblemLabels() {
        return boldProblemLabels.isSelected();
    }

    public void setBoldProblemLabels(boolean isSelected) {
        boldProblemLabels.setSelected(isSelected);
    }

    public boolean isItalicProblemLabels() {
        return italicProblemLabels.isSelected();
    }

    public void setItalicProblemLabels(boolean isSelected) {
        italicProblemLabels.setSelected(isSelected);
    }

    public boolean isShowErrors() {
        return showErrors.isSelected();
    }

    public void setShowErrors(final boolean isSelected) {
        showErrors.setSelected(isSelected);
    }

    public boolean isHighlightErrors() {
        return highlightErrors.isSelected();
    }

    public void setHighlightErrors(boolean isSelected) {
        highlightErrors.setSelected(isSelected);
    }

    public boolean isShowWarnings() {
        return showWarnings.isSelected();
    }

    public void setShowWarnings(final boolean isSelected) {
        showWarnings.setSelected(isSelected);
    }

    public boolean isHighlightWarnings() {
        return highlightWarnings.isSelected();
    }

    public void setHighlightWarnings(final boolean isSelected) {
        highlightWarnings.setSelected(isSelected);
    }

    public boolean isShowWeakWarnings() {
        return showWeakWarnings.isSelected();
    }

    public void setShowWeakWarnings(final boolean isSelected) {
        showWeakWarnings.setSelected(isSelected);
    }

    public boolean isHighlightWeakWarnings() {
        return highlightWeakWarnings.isSelected();
    }

    public void setHighlightWeakWarnings(final boolean isSelected) {
        highlightWeakWarnings.setSelected(isSelected);
    }

    public boolean isShowInfos() {
        return showInfos.isSelected();
    }

    public void setShowInfos(final boolean isSelected) {
        showInfos.setSelected(isSelected);
    }

    public boolean isHighlightInfo() {
        return highlightInfo.isSelected();
    }

    public void setHighlightInfo(final boolean isSelected) {
        highlightInfo.setSelected(isSelected);
    }

    public Color getErrorTextColor() {
        return errorTextColor.getSelectedColor();
    }

    public void setErrorTextColor(final Color color) {
        errorTextColor.setSelectedColor(color);
    }

    public Color getErrorLabelBackgroundColor() {
        return errorLabelBackgroundColor.getSelectedColor();
    }

    public void setErrorLabelBackgroundColor(final Color color) {
        errorLabelBackgroundColor.setSelectedColor(color);
    }

    public Color getWarningTextColor() {
        return warningTextColor.getSelectedColor();
    }

    public void setWarningTextColor(final Color color) {
        warningTextColor.setSelectedColor(color);
    }

    public Color getWarningLabelBackgroundColor() {
        return warningLabelBackgroundColor.getSelectedColor();
    }

    public void setWarningLabelBackgroundColor(final Color color) {
        warningLabelBackgroundColor.setSelectedColor(color);
    }

    public Color getWeakWarningTextColor() {
        return weakWarningTextColor.getSelectedColor();
    }

    public void setWeakWarningTextColor(final Color color) {
        weakWarningTextColor.setSelectedColor(color);
    }

    public Color getWeakWarningLabelBackgroundColor() {
        return weakWarningLabelBackgroundColor.getSelectedColor();
    }

    public void setWeakWarningLabelBackgroundColor(final Color color) {
        weakWarningLabelBackgroundColor.setSelectedColor(color);
    }

    public Color getInfoTextColor() {
        return infoTextColor.getSelectedColor();
    }

    public void setInfoTextColor(final Color color) {
        infoTextColor.setSelectedColor(color);
    }

    public Color getInfoLabelBackgroundColor() {
        return infoLabelBackgroundColor.getSelectedColor();
    }

    public void setInfoLabelBackgroundColor(final Color color) {
        infoLabelBackgroundColor.setSelectedColor(color);
    }

    public Color getErrorHighlightColor() {
        return errorHighlightColor.getSelectedColor();
    }

    public void setErrorHighlightColor(final Color color) {
        errorHighlightColor.setSelectedColor(color);
    }

    public Color getWarningHighlightColor() {
        return warningHighlightColor.getSelectedColor();
    }

    public void setWarningHighlightColor(final Color color) {
        warningHighlightColor.setSelectedColor(color);
    }

    public Color getWeakWarningHighlightColor() {
        return weakWarningHighlightColor.getSelectedColor();
    }

    public void setWeakWarningHighlightColor(final Color color) {
        weakWarningHighlightColor.setSelectedColor(color);
    }

    public Color getInfoHighlightColor() {
        return infoHighlightColor.getSelectedColor();
    }

    public void setInfoHighlightColor(final Color color) {
        infoHighlightColor.setSelectedColor(color);
    }

    public String getProblemFilterList() {
        return problemFilterList.getText();
    }

    public void setProblemFilterList(final String newText) {
        problemFilterList.setText(newText);
    }

    public String getAdditionalInfoSeverities() {
        return additionalInfoSeverities.getText();
    }

    public List<Integer> getAdditionalInfoSeveritiesList() {
        return getSeverityIntegerList(additionalInfoSeverities.getText());
    }

    public void setAdditionalInfoSeverities(final String newText) {
        additionalInfoSeverities.setText(newText);
    }

    public String getAdditionalWarningSeverities() {
        return additionalWarningSeverities.getText();
    }

    public List<Integer> getAdditionalWarningSeveritiesList() {
        return getSeverityIntegerList(additionalWarningSeverities.getText());
    }

    public void setAdditionalWarningSeverities(final String newText) {
        additionalWarningSeverities.setText(newText);
    }

    public String getAdditionalErrorSeverities() {
        return additionalErrorSeverities.getText();
    }

    public List<Integer> getAdditionalErrorSeveritiesList() {
        return getSeverityIntegerList(additionalErrorSeverities.getText());
    }

    public void setAdditionalErrorSeverities(final String newText) {
        additionalErrorSeverities.setText(newText);
    }

    public String getAdditionalWeakWarningSeverities() {
        return additionalWeakWarningSeverities.getText();
    }

    public List<Integer> getAdditionalWeakWarningSeveritiesList() {
        return getSeverityIntegerList(additionalWeakWarningSeverities.getText());
    }

    public void setAdditionalWeakWarningSeverities(final String newText) {
        additionalWeakWarningSeverities.setText(newText);
    }

    public int getEnabledListener() {
        return enabledListener.getSelectedIndex();
    }

    public void setEnabledListener(int index) {
        enabledListener.setSelectedIndex(index);
    }

    private List<Integer> getSeverityIntegerList(String text) {
        return Arrays.stream(text.split(";"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .filter(s -> {
                    try {
                        Integer.parseInt(s);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public int getManualScannerDelay() {
        try {
            return Math.max(Integer.parseInt(manualScannerDelay.getText()), 10);
        }
        catch (NumberFormatException e) {
            return 100;
        }
    }

    public void setManualScannerDelay(int delay) {
        manualScannerDelay.setText(Integer.toString(Math.max(10, delay)));
    }
}
