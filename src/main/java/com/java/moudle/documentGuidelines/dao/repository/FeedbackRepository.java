package com.java.moudle.documentGuidelines.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.moudle.documentGuidelines.domain.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, String>{

}
