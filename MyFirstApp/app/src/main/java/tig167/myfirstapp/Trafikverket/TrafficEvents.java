package tig167.myfirstapp.Trafikverket;

public class TrafficEvents {


        private String messagetype;
        private String summary;
        private String location;

        public TrafficEvents(String messagetype, String summary, String location) {

            this.messagetype = messagetype;
            this.summary = summary;
            this.location = location;
        }

        public String messagetype() {return messagetype;}

        public String summary() {return summary;}

        public String location() {return location;}

        /*
        @Override
        public String toString() {
            return new StringBuilder()
                    .append("Beskrivning: " + messagetype)
                    .append("\n")
                    .append("Orsak: " + summary)
                    .append("\n")
                    .append("Location: " + location)
                    .append("\n")
                    .toString();
        }

        */

    }



