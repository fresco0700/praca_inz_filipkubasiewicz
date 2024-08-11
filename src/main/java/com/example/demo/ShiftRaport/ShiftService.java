package com.example.demo.ShiftRaport;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public List<Map<String, Object>> getShiftPosts(int page, int size) {
        Pageable top = PageRequest.of(page, size);
        Page<ShiftPost> firstTenPosts = shiftRepository.findAllByVisibleIsTrueOrderByEditedDateDesc(top);
        List<ShiftPost> posts = firstTenPosts.getContent();
        List<Map<String, Object>> postMaps = new ArrayList<>();

        for (ShiftPost post : posts) {
            Map<String, Object> postMap = objectMapper.convertValue(post, Map.class);
            postMap.put("editedDate", post.getFormattedEditedDate());
            List<Like> likes = post.getLikes();
            postMap.put("likes", likes);
            postMaps.add(postMap);
        }

        return postMaps;
    }
    public List<String> getFilesForShiftPost(Long id) {
        Optional<ShiftPost> shiftPostOptional = shiftRepository.findShiftPostById(id);
        if(shiftPostOptional.isPresent()){
            ShiftPost shiftPost = shiftPostOptional.get();
            return shiftPost.getFiles();
        }
        return new ArrayList<>();
    }
    public void addNewShiftPost(ShiftPost shiftPost) {
        shiftRepository.save(shiftPost);
    }

    @Transactional
    public void deleteShiftPost(Long id, Boolean visible) {
        shiftRepository.updateVisibilityById(id,visible);}

    @Transactional
    public void updateShiftPost(Long id, String title, String content, String state, String editedBy, LocalDateTime editedDate, List<String> filenames) {

        ShiftPost shiftPost = shiftRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Ten wpis jużnie istnieje"));
        shiftPost.setTitle(title);
        shiftPost.setContent(content);
        shiftPost.setState(state);
        shiftPost.setEditedBy(editedBy);
        shiftPost.setEditedDate(editedDate);
        shiftPost.addFiles(filenames);
    }

    public boolean likePost(Long postId, String username) {
        try{
        ShiftPost shiftPost = shiftRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
            shiftPost.addLike(username);
            shiftRepository.save(shiftPost);
            return true;
        } catch (RuntimeException e) {
            return false;
        }

    }
}
