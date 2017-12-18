package com.bad_coders.moneyconverter.Model;

import java.util.List;

/**
 * Created on 17.12.2017.
 */


public class BankResponse {
    private List<Bank> results;

    public List<Bank> getResults() {
        return results;
    }

    public class Bank {
        private Geometry geometry;
        private String name;
        private String vicinity;

        public Bank(double lng, double lat, String name, String vicinity) {
            this.name = name;
            this.vicinity = vicinity;
        }

        public String getName() {
            return name;
        }

        public String getVicinity() {
            return vicinity;
        }

        public Geometry getGeometry() {
            return geometry;
        }
    }

    public class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }
    }

    public class Location {
        private double lng;
        private double lat;

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }
    }
}