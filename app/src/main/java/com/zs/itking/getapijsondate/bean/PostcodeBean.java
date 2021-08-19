package com.zs.itking.getapijsondate.bean;

import java.util.List;

/**
 * created by on 2021/8/19
 * 描述：
 *
 * @author ZSAndroid
 * @create 2021-08-19-23:45
 */
public class PostcodeBean {


    private String reason;
    private Result result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class Result {
        private List<list> list;
        private int totalcount;
        private int totalpage;
        private int currentpage;
        private String pagesize;

        public List<list> getList() {
            return list;
        }

        public void setList(List<list> list) {
            this.list = list;
        }

        public int getTotalcount() {
            return totalcount;
        }

        public void setTotalcount(int totalcount) {
            this.totalcount = totalcount;
        }

        public int getTotalpage() {
            return totalpage;
        }

        public void setTotalpage(int totalpage) {
            this.totalpage = totalpage;
        }

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }

        public String getPagesize() {
            return pagesize;
        }

        public void setPagesize(String pagesize) {
            this.pagesize = pagesize;
        }

        public static class list {
            private String postNumber;
            private String province;
            private String city;
            private String district;
            private String address;

            public String getPostNumber() {
                return postNumber;
            }

            public void setPostNumber(String postNumber) {
                this.postNumber = postNumber;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
