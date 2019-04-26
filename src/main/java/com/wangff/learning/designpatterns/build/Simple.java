package com.wangff.learning.designpatterns.build;


import java.io.Serializable;


public class Simple implements Serializable {

    private Long id;
    private Long courseId;
    private Long studyGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getStudyGroupId() {
        return studyGroupId;
    }

    public void setStudyGroupId(Long studyGroupId) {
        this.studyGroupId = studyGroupId;
    }

    public Simple() {
    }

    public Simple(Long id, Long courseId, Long studyGroupId) {
        this.id = id;
        this.courseId = courseId;
        this.studyGroupId = studyGroupId;
    }

    public static SimpleBuilder builder() {
        return new SimpleBuilder();
    }


    public static class SimpleBuilder {
        private Long id;
        private Long courseId;
        private Long studyGroupId;

        SimpleBuilder() {
        }

        public SimpleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SimpleBuilder courseId(Long courseId) {
            this.courseId = courseId;
            return this;
        }

        public SimpleBuilder studyGroupId(Long studyGroupId) {
            this.studyGroupId = studyGroupId;
            return this;
        }

        public Simple build() {
            return new Simple(this.id, this.courseId, this.studyGroupId);
        }
    }

}

