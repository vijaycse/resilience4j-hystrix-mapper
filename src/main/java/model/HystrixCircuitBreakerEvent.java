package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HystrixCircuitBreakerEvent {

        @SerializedName("traversableAgain")
        @Expose
        private Boolean traversableAgain;
        @SerializedName("async")
        @Expose
        private Boolean async;
        @SerializedName("lazy")
        @Expose
        private Boolean lazy;
        @SerializedName("empty")
        @Expose
        private Boolean empty;
        @SerializedName("sequential")
        @Expose
        private Boolean sequential;
        @SerializedName("distinct")
        @Expose
        private Boolean distinct;
        @SerializedName("singleValued")
        @Expose
        private Boolean singleValued;
        @SerializedName("ordered")
        @Expose
        private Boolean ordered;
        @SerializedName("orNull")
        @Expose
        private OrNull orNull;
        @SerializedName("memoized")
        @Expose
        private Boolean memoized;

        public Boolean getTraversableAgain() {
            return traversableAgain;
        }

        public void setTraversableAgain(Boolean traversableAgain) {
            this.traversableAgain = traversableAgain;
        }

        public Boolean getAsync() {
            return async;
        }

        public void setAsync(Boolean async) {
            this.async = async;
        }

        public Boolean getLazy() {
            return lazy;
        }

        public void setLazy(Boolean lazy) {
            this.lazy = lazy;
        }

        public Boolean getEmpty() {
            return empty;
        }

        public void setEmpty(Boolean empty) {
            this.empty = empty;
        }

        public Boolean getSequential() {
            return sequential;
        }

        public void setSequential(Boolean sequential) {
            this.sequential = sequential;
        }

        public Boolean getDistinct() {
            return distinct;
        }

        public void setDistinct(Boolean distinct) {
            this.distinct = distinct;
        }

        public Boolean getSingleValued() {
            return singleValued;
        }

        public void setSingleValued(Boolean singleValued) {
            this.singleValued = singleValued;
        }

        public Boolean getOrdered() {
            return ordered;
        }

        public void setOrdered(Boolean ordered) {
            this.ordered = ordered;
        }

        public OrNull getOrNull() {
            return orNull;
        }

        public void setOrNull(OrNull orNull) {
            this.orNull = orNull;
        }

        public Boolean getMemoized() {
            return memoized;
        }

        public void setMemoized(Boolean memoized) {
            this.memoized = memoized;
        }

    }
