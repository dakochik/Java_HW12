package objects;

/**
 * Represents the address location, where user can lives.
 */
public class Address {
    /**
     * Address country name
     */
    private final String country;

    /**
     * Address town name
     */
    private final String town;

    /**
     * Address street name
     */
    private final String street;

    /**
     * Address home number
     */
    private final int homeNumb;

    public String getCountry() {
        return country;
    }

    public String getTown() {
        return town;
    }

    public String getStreet() {
        return street;
    }

    public int getHomeNumb() {
        return homeNumb;
    }

    /**
     * Constructor of Address class
     *
     * @param country  country name
     * @param town     town name
     * @param street   street name
     * @param homeNumb house number. Have to be positive
     * @throws IllegalArgumentException if house number isn't positive
     */
    public Address(String country, String town, String street, int homeNumb) throws IllegalArgumentException {
        this.country = country;
        this.town = town;
        this.street = street;

        if (homeNumb <= 0) {
            throw new IllegalArgumentException("Home number can't be non natural");
        }

        this.homeNumb = homeNumb;
    }

    /**
     * Checks if two objects are the same.
     *
     * @param obj another objects, with which we need to compare current obj.
     * @return returns true if two objects are descendants of Address class and if all of the fields are
     * the same, otherwise it returns false;
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Address) {
            return country.toLowerCase().equals(((Address) obj).country.toLowerCase())
                    && town.toLowerCase().equals(((Address) obj).town.toLowerCase())
                    && street.toLowerCase().equals(((Address) obj).street.toLowerCase())
                    && (homeNumb == ((Address) obj).homeNumb);

        }

        return false;
    }
}
