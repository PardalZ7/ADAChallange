package br.com.ada.challange.domain.enums;

public enum QuizResponse {

    A, B;

    public QuizResponse enumOf(String value) {
        if (value.equals("A"))
            return QuizResponse.A;
        if (value.equals("B"))
            return QuizResponse.B;

        return null;
    }

}
