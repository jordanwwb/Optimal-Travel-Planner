/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Tag;
import java.util.List;
import javax.ejb.Local;
import util.exception.DeleteTagException;
import util.exception.TagAlreadyExistException;
import util.exception.TagNotFoundException;

/**
 *
 * @author sucram
 */
@Local
public interface TagSessionBeanLocal {

    /**
     *
     * @return
     */
    public List<Tag> retrieveAllTags();

    /**
     *
     * @param newTag
     * @return
     * @throws TagAlreadyExistException
     */
    public Tag createNewTag(Tag newTag) throws TagAlreadyExistException;

    /**
     *
     * @param tagId
     * @return
     * @throws TagNotFoundException
     */
    public Tag retrieveTagByTagId(Long tagId) throws TagNotFoundException;

    /**
     *
     * @param tagId
     * @throws DeleteTagException
     */
    public void deleteTag(Long tagId) throws DeleteTagException;

    /**
     *
     * @param newTag
     * @return
     */
    public Tag updateTag(Tag newTag);
    
}
