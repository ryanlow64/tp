package seedu.address.model.deal;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.deal.exceptions.DealNotFoundException;
import seedu.address.model.deal.exceptions.DuplicateDealException;

/**
 * A list of deals that enforces uniqueness between its elements and does not allow nulls.
 * A deal is considered unique by comparing using {@code Deal#isSameDeal(Deal)}. As such, adding and
 * updating of deals uses Deal#isSameDeal(Deal) for equality so as to ensure that the deal being
 * added or updated is unique in terms of identity in the UniqueDealList. However, the removal of a deal uses
 * Deal#equals(Object) so as to ensure that the deal with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Deal#isSameDeal(Deal)
 */
public class UniqueDealList implements Iterable<Deal> {

    private final ObservableList<Deal> internalList = FXCollections.observableArrayList();
    private final ObservableList<Deal> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent deal as the given argument.
     */
    public boolean contains(Deal toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDeal);
    }

    /**
     * Adds a deal to the list.
     * The deal must not already exist in the list.
     */
    public void add(Deal toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateDealException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the deal {@code target} in the list with {@code editedDeal}.
     * {@code target} must exist in the list.
     * The deal identity of {@code editedDeal} must not be the same as another existing deal in the list.
     */
    public void setDeal(Deal target, Deal editedDeal) {
        requireAllNonNull(target, editedDeal);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DealNotFoundException();
        }

        if (!target.isSameDeal(editedDeal) && contains(editedDeal)) {
            throw new DuplicateDealException();
        }

        internalList.set(index, editedDeal);
    }

    /**
     * Removes the equivalent deal from the list.
     * The deal must exist in the list.
     */
    public void remove(Deal toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DealNotFoundException();
        }
    }

    public void setDeals(UniqueDealList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code deals}.
     * {@code deals} must not contain duplicate deals.
     */
    public void setDeals(List<Deal> deals) {
        requireAllNonNull(deals);
        if (!dealsAreUnique(deals)) {
            throw new DuplicateDealException();
        }

        internalList.setAll(deals);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Deal> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Deal> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueDealList)) {
            return false;
        }

        UniqueDealList
            otherUniqueDealList = (UniqueDealList) other;
        return internalList.equals(otherUniqueDealList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code deals} contains only unique deals.
     */
    private boolean dealsAreUnique(List<Deal> deals) {
        for (int i = 0; i < deals.size() - 1; i++) {
            for (int j = i + 1; j < deals.size(); j++) {
                if (deals.get(i).isSameDeal(deals.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
