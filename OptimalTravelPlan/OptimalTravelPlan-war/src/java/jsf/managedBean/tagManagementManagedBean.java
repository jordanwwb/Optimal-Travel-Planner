/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import ejb.session.stateless.TagSessionBeanLocal;
import entity.Tag;
import java.io.IOException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.PrimeFaces;
import util.exception.DeleteTagException;
import util.exception.TagAlreadyExistException;

/**
 *
 * @author sucram
 */
@Named(value = "tagManagementManagedBean")
@ViewScoped
public class tagManagementManagedBean implements Serializable {

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;

    private List<Tag> tags;
    private List<Tag> filteredTags;
    private Boolean filtered;
    private Tag newTag;
    private Tag tagToUpdate;

    /**
     *
     */
    public tagManagementManagedBean() {
        newTag = new Tag();
    }

    /**
     *
     */
    @PostConstruct
    public void post() {
        List<Tag> selectedTags = (List<Tag>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("tagsToView");
        if (selectedTags == null) {
            tags = tagSessionBeanLocal.retrieveAllTags();
            filtered = false;
        } else {
            tags = selectedTags;
            filtered = true;
        }
        Boolean addTag = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("addNewTag");
        if (addTag != null && addTag) {
            PrimeFaces.current().executeScript("PF('dialogCreateNewTag').show();");
        }
    }

    /**
     *
     * @param event
     */
    public void refreshTagsList(ActionEvent event) {
        this.tags = tagSessionBeanLocal.retrieveAllTags();
        this.filtered = false;
    }

    /**
     *
     * @param event
     */
    public void createNewTag(ActionEvent event) {
        try {
            Tag t = tagSessionBeanLocal.createNewTag(newTag);
            tags.add(t);
            newTag = new Tag();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New tag created successfully (Tag ID: " + t.getTagId() + ")", null));
        } catch (TagAlreadyExistException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to create tag: " + e.getMessage(), null));
        }
    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void viewTagServices(ActionEvent event) throws IOException {
        Tag selectedTag = (Tag) event.getComponent().getAttributes().get("tagToViewServices");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("servicesToView", selectedTag.getServices());
        FacesContext.getCurrentInstance().getExternalContext().redirect("serviceManagement.xhtml");
    }

    /**
     *
     * @param event
     */
    public void doUpdateTag(ActionEvent event) {
        tagToUpdate = (Tag) event.getComponent().getAttributes().get("tagToUpdate");
    }

    /**
     *
     * @param event
     */
    public void updateTag(ActionEvent event) {
        Tag t = tagSessionBeanLocal.updateTag(tagToUpdate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag updated successfully", null));
    }

    /**
     *
     * @param event
     */
    public void deleteTag(ActionEvent event) {
        try {
            Tag tagToDelete = (Tag) event.getComponent().getAttributes().get("tagToDelete");
            tagSessionBeanLocal.deleteTag(tagToDelete.getTagId());
            tags.remove(tagToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tag deleted successfully", null));
        } catch (DeleteTagException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Unable to delete:" + ex.getMessage(), null));
        }
    }

    /**
     *
     * @return
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     */
    public List<Tag> getFilteredTags() {
        return filteredTags;
    }

    /**
     *
     * @param filteredTags
     */
    public void setFilteredTags(List<Tag> filteredTags) {
        this.filteredTags = filteredTags;
    }

    /**
     *
     * @return
     */
    public Tag getNewTag() {
        return newTag;
    }

    /**
     *
     * @param newTag
     */
    public void setNewTag(Tag newTag) {
        this.newTag = newTag;
    }

    /**
     *
     * @return
     */
    public Tag getTagToUpdate() {
        return tagToUpdate;
    }

    /**
     *
     * @param tagToUpdate
     */
    public void setTagToUpdate(Tag tagToUpdate) {
        this.tagToUpdate = tagToUpdate;
    }

    /**
     *
     * @return
     */
    public Boolean getFiltered() {
        return filtered;
    }

    /**
     *
     * @param filtered
     */
    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }
}
