package me.androidbox.flicks.model;

import java.util.List;

/**
 * Created by steve on 10/17/16.
 */

public class Videos {
    private String id;
    private List<Results> results;

    public String getId() {
        return id;
    }

    public List<Results> getResults() {
        return results;
    }

    public static class Results {
        private String id;
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private String size;
        private String type;

        public String getId() {
            return id;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public String getSite() {
            return site;
        }

        public String getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }
}

