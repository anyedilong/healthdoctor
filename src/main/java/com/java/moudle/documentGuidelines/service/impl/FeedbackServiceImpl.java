package com.java.moudle.documentGuidelines.service.impl;


import javax.inject.Named;

import com.java.moudle.common.service.impl.BaseServiceImpl;
import com.java.moudle.documentGuidelines.dao.FeedbackDao;
import com.java.moudle.documentGuidelines.domain.Feedback;
import com.java.moudle.documentGuidelines.service.FeedbackService;

@Named
public class FeedbackServiceImpl extends BaseServiceImpl<FeedbackDao, Feedback> implements FeedbackService{

}
