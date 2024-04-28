package com.example.surveylib.survey;

import com.google.gson.annotations.SerializedName;

public class SurveyConfig {
    String orgId, spath;
    int active;
    @SerializedName("ppupsrv")
    public Sur srv;

    @Override
    public String toString() {
        return "SurveyConfig{" +
                "orgId='" + orgId + '\'' +
                ", spath='" + spath + '\'' +
                ", active=" + active +
                ", srv=" + srv +
                '}';
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getSpath() {
        return spath;
    }

    public void setSpath(String spath) {
        this.spath = spath;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Sur getSrv() {
        return srv;
    }

    public void setSrv(Sur srv) {
        this.srv = srv;
    }

    private class Sur{
        String urlmatch, url,interval,device,srvshowfrq,trigger,theme,thnkMag;

        @Override
        public String toString() {
            return "Sur{" +
                    "urlmatch='" + urlmatch + '\'' +
                    ", url='" + url + '\'' +
                    ", interval='" + interval + '\'' +
                    ", device='" + device + '\'' +
                    ", srvshowfrq='" + srvshowfrq + '\'' +
                    ", trigger='" + trigger + '\'' +
                    ", theme='" + theme + '\'' +
                    ", thnkMag='" + thnkMag + '\'' +
                    '}';
        }

        public String getUrlmatch() {
            return urlmatch;
        }

        public void setUrlmatch(String urlmatch) {
            this.urlmatch = urlmatch;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public String getDevice() {
            return device;
        }

        public void setDevice(String device) {
            this.device = device;
        }

        public String getSrvshowfrq() {
            return srvshowfrq;
        }

        public void setSrvshowfrq(String srvshowfrq) {
            this.srvshowfrq = srvshowfrq;
        }

        public String getTrigger() {
            return trigger;
        }

        public void setTrigger(String trigger) {
            this.trigger = trigger;
        }

        public String getTheme() {
            return theme;
        }

        public void setTheme(String theme) {
            this.theme = theme;
        }

        public String getThnkMag() {
            return thnkMag;
        }

        public void setThnkMag(String thnkMag) {
            this.thnkMag = thnkMag;
        }
    }
}
