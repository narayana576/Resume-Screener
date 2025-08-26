package com.resumescreener.backend.utils;

import java.util.*;
import java.util.stream.Collectors;

public class SkillExtractor {

    // Known fine-grained skills we detect directly
    private static final List<String> KNOWN_SKILLS = Arrays.asList(
            "java", "spring boot", "mongodb", "postgresql", "mysql", "sql",
            "python", "rest api", "rest apis", "restful", "react", "node.js",
            "docker", "aws", "git", "kubernetes", "hibernate"
    );

    // Synonym categories
    private static final Map<String, List<String>> CATEGORY_SYNONYMS = Map.of(
            "database", List.of("mongodb", "mysql", "postgresql", "oracle", "sql"),
            "api", List.of("rest api", "rest apis", "restful"),
            "cloud", List.of("aws", "azure", "gcp"),
            "devops", List.of("docker", "kubernetes"),
            "backend", List.of("java", "python", "node.js", "spring boot")
    );

    // Lowercase & strip punctuation
    private static String normalize(String s) {
        if (s == null) return "";
        return s.toLowerCase()
                .replaceAll("[^a-z0-9+.\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    /**
     * Extract known skills from resume text
     */
    public static List<String> extractSkills(String resumeText) {
        String normalized = normalize(resumeText);
        return KNOWN_SKILLS.stream()
                .filter(k -> normalized.contains(k))
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Extract skills from Job Description into a clean list
     */
    public static List<String> extractJDSkills(String jobDescription) {
        if (jobDescription == null || jobDescription.isBlank()) return Collections.emptyList();

        String normalized = normalize(
                jobDescription.replaceAll("\\band\\b", ",")
                        .replaceAll("\\bor\\b", ",")
                        .replaceAll(";", ",")
        );

        // Tokenize and only keep relevant skill tokens from known list or synonyms
        Set<String> jdSkills = new LinkedHashSet<>();
        for (String token : normalized.split(",")) {
            token = token.trim();
            if (token.isEmpty()) continue;

            for (String skill : KNOWN_SKILLS) {
                if (token.contains(skill)) {
                    jdSkills.add(skill);
                }
            }

            // Check for category match
            for (Map.Entry<String, List<String>> entry : CATEGORY_SYNONYMS.entrySet()) {
                if (token.contains(entry.getKey())) {
                    jdSkills.addAll(entry.getValue());
                }
            }
        }

        return new ArrayList<>(jdSkills);
    }

    /**
     * Calculate % of JD skills matched by resume extracted skills.
     */
    public static double calculateMatchScore(List<String> extractedSkills, String jobDescription) {
        List<String> jdSkills = extractJDSkills(jobDescription);
        if (jdSkills.isEmpty() || extractedSkills.isEmpty()) return 0.0;

        // Normalize sets for matching
        Set<String> jdSet = jdSkills.stream().map(String::toLowerCase).collect(Collectors.toSet());
        Set<String> resumeSet = extractedSkills.stream().map(String::toLowerCase).collect(Collectors.toSet());

        // Count matches
        long matches = jdSet.stream().filter(resumeSet::contains).count();

        // Score is matches ÷ JD skills count
        double score = (matches * 100.0) / jdSet.size();
        return Math.round(score * 100.0) / 100.0;
    }
}