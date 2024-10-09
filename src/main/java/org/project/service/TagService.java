package org.project.service;

import org.project.entite.Tag;
import org.project.repositorie.TagRepository;

import java.util.List;

public class TagService {
    private TagRepository tagRepository;
    public TagService(){
        tagRepository = new TagRepository();
    }
    public List<Tag> getALlTags(){
        return tagRepository.getAllTags();
    }
    public Tag getById(int id){
        return tagRepository.getTagById(id);
    }
    public void createTag(Tag tag){

        tagRepository.createTag(tag) ;
    }
//    public void deleteTag(int id){
//        tagRepository.deleteTag(id);
//    }
}
