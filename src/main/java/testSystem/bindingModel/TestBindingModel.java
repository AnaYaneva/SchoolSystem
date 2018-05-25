package testSystem.bindingModel;

import javax.validation.constraints.NotNull;

public class TestBindingModel {
    @NotNull
    private String title;

    @NotNull
    private String content;

    private Integer subjectId;

    private String tagString;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSubjectId() {
        return this.subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public String getTagString() {
        return this.tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }
}
