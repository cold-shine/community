package xin.coldshine.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.coldshine.community.dto.QuestionDTO;
import xin.coldshine.community.mapper.QuestionMapper;
import xin.coldshine.community.mapper.UserMapper;
import xin.coldshine.community.model.Question;
import xin.coldshine.community.model.User;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
           User user =  userMapper.findById(question.getCreator());
           QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        return questionDTOList;

    }
}
