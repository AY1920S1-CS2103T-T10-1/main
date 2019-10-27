package seedu.jarvis.storage.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.jarvis.commons.exceptions.IllegalValueException;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.storage.JsonAdapter;

/**
 * A {@code FinanceTracker} that is serializable to JSON format.
 */
@JsonRootName(value = "financetracker")
public class JsonSerializableFinanceTracker implements JsonAdapter<FinanceTracker> {

    public static final String MESSAGE_DUPLICATE_FINANCES = "Finances contains duplicate installment(s) / purchase(s)";

    private final List<JsonAdaptedInstallment> installments = new ArrayList<>();
    private final List<JsonAdaptedPurchase> purchases = new ArrayList<>();

    @JsonCreator
    public JsonSerializableFinanceTracker(@JsonProperty("installments") List<JsonAdaptedInstallment> installments,
                                          @JsonProperty("purchases") List<JsonAdaptedPurchase> purchases) {
        this.installments.addAll(installments);
        this.purchases.addAll(purchases);
    }

    public JsonSerializableFinanceTracker(FinanceTracker financeTracker) {
        installments.addAll(financeTracker.getInstallmentList()
                .stream()
                .map(JsonAdaptedInstallment::new)
                .collect(Collectors.toList()));
        purchases.addAll(financeTracker.getPurchaseList()
                .stream()
                .map(JsonAdaptedPurchase::new)
                .collect(Collectors.toList()));
    }

    @Override
    public FinanceTracker toModelType() throws IllegalValueException {
        FinanceTracker financeTracker = new FinanceTracker();
        for (JsonAdaptedInstallment jsonAdaptedInstallment : installments) {
            Installment installment = jsonAdaptedInstallment.toModelType();
            if (financeTracker.hasInstallment(installment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FINANCES);
            }
            financeTracker.addInstallment(installment);
        }
        for (JsonAdaptedPurchase jsonAdaptedPurchase : purchases) {
            Purchase purchase = jsonAdaptedPurchase.toModelType();
            if (financeTracker.hasPurchase(purchase)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_FINANCES);
            }
            financeTracker.addSinglePurchase(purchase);
        }
        return financeTracker;
    }
}
