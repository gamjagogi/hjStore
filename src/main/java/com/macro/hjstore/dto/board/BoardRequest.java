package com.macro.hjstore.dto.board;

import com.macro.hjstore.model.board.Board;
import com.macro.hjstore.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class BoardRequest {
    @Setter
    @Getter
    public static class SaveInDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;

        public Board toEntity(User user, String thumbnail) {
            return Board.builder()
                    .user(user)
                    .title(title)
                    .content(content)
                    .thumbnail(thumbnail)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class UpdateInDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
    }
}