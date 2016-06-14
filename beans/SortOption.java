package awesomecars.beans;

/** 
 * Helper object for grouping sort options in AdvancedSearch.
 * 
 * @author Travis
 */
public class SortOption {
    /** Parameter name to sort by. */
    private String sortBy;
    
    /** Order to sort (ascending/descending). */
    private String sortOrder;

    /** Constructor. 
     * @param param Name of parameter to sort by
     * @param order Direction to sort (ascending/descending) */
    public SortOption(final String param, final String order) {
        sortBy = param;
        sortOrder = order;
    }
    
    /** @return the parameter name */
    public final String getSortBy() { 
        return sortBy; 
    }

    /** @param param the parameter name to set */
    public final void setSortBy(final String param) { 
        this.sortBy = param; 
    }

    /** @return the sort order (ASC/DESC) */
    public final String getSortOrder() { 
        return sortOrder; 
    }

    /** @param order the sort order to set (ASC/DESC) */
    public final void setSortOrder(final String order) { 
        this.sortOrder = order; 
    }
    
    /** @return the SortOption converted to SQL string */
    public final String toString() {
        return " " + sortBy + " " + sortOrder + " ";
    }
}
