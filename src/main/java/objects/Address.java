package objects;


public class Address {
    private String country;
    private String town;
    private String street;
    private int homeNumb;

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

    public Address(String country, String town, String street, int homeNumb) throws IllegalArgumentException{
        this.country = country;
        this.town = town;
        this.street = street;

        if(homeNumb<=0){
            throw new IllegalArgumentException("Home number can't be non natural");
        }

        this.homeNumb = homeNumb;
    }

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
