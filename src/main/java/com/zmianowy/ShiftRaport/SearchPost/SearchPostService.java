package com.zmianowy.ShiftRaport.SearchPost;

import com.zmianowy.ShiftRaport.ShiftPost;
import com.zmianowy.ShiftRaport.ShiftRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPostService {

    private final ShiftRepository shiftRepository;

    @Transactional
    public List<ShiftPost> findPosts(String content, String title,String state,String startDate, String endDate) {
        return shiftRepository.findByContentContainingIgnoreCaseAndTitleContainingIgnoreCaseAndStateContainingIgnoreCaseAndLocalDateBetween(content,title,state,startDate,endDate);

    }
    @Transactional
    public List<ShiftPost> findPostsByVisibility(String content, String title,String state,String startDate, String endDate,Boolean visible) {
        return shiftRepository.findByContentContainingIgnoreCaseAndTitleContainingIgnoreCaseAndStateContainingIgnoreCaseAndLocalDateBetweenAndVisible(content,title,state,startDate,endDate,visible);

    }

    @Transactional
    public void manageShiftPost(Long id, boolean visible) { shiftRepository.updateVisibilityById(id,visible);}

}
