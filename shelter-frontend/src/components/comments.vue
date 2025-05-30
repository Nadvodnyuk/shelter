<template>
  <br />
  <div class="comments" v-show="commentFlags[animalId]">
    <div
      v-if="
        role === 'ROLE_ADMIN' &&
        commentAll[animalId] &&
        commentAll[animalId].length
      "
      class="title_comment"
    >
      <br />
      <div v-for="(comment, index) in commentAll[animalId]" :key="index">
        <div class="comment_trash">
          {{ comment.comment_phone }}
        </div>
        <div class="comment_trash">
          {{ comment.comment_text }}
        </div>
        <p class="comment_date">
          {{ formatDate(comment.commentDate) }} {{ comment.userC.first_name }}
          {{ comment.userC.last_name }}
          <button
            @click="deleteComment(comment.comment_id)"
            v-if="role === 'ROLE_ADMIN'"
            class="nostyle"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="16"
              height="16"
              fill="currentColor"
              class="bi bi-trash"
              viewBox="0 0 16 16"
            >
              <path
                d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"
              />
              <path
                fill-rule="evenodd"
                d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"
              />
            </svg>
          </button>
        </p>
      </div>
    </div>
    <div v-else>
      <p v-if="!role">
        Чтобы оставить заявку,
        <router-link to="/login/">Войдите</router-link> или
        <router-link to="/reg/">Зарегистрируйтесь</router-link>!
      </p>
      <p v-else-if="role !== 'ROLE_ADMIN'">
        Если хотите забрать животное, оставьте заявку здесь!
      </p>
    </div>
    <div class="comment_more">
      <ul class="state">
        <li>
          <button
            class="nostyle"
            @click="fetchMoreComments()"
            v-if="
              this.commentPages[this.animalId] < commentNums[this.animalId] / 3
            "
          >
            Ещё комментарии
          </button>
        </li>
      </ul>
    </div>
    <div v-if="role === 'ROLE_USER'">
      <div class="comment_bar">
        <textarea
          type="input"
          cols="60"
          rows="14"
          wrap="soft"
          class="leave_comment"
          v-model="comment_text.comment_phone"
          @input="validatePhone"
          placeholder="Ваш номер телефона"
        >
        </textarea>
      </div>
      <div class="comment_bar">
        <textarea
          type="input"
          cols="60"
          rows="14"
          wrap="soft"
          class="leave_comment"
          v-model="comment_text.comment_text"
          @input="validateComment"
          placeholder="Комментарий к заявке"
        >
        </textarea>
      </div>
      <p v-if="errors.comment">
        {{ errors.comment }}
      </p>
      <div class="comment_more">
        <ul class="state">
          <li>
            <button class="put_comm_btn" @click="submitComment()">
              Отправить заявку
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import { useCatalog } from "../store/catalog.js";
import { mapState, mapActions } from "pinia";
import HomeDataService from "../services/HomeDataService";

export default {
  name: "comments",
  data() {
    return {
      commentFlag: false,
      moreCommentsFlag: false,
      sentFlag: false,
      errors: {
        comment: "",
        phone: "",
      },
      comment_text: {
        comment_phone: "",
        comment_text: "",
      },
      pages: {},
    };
  },
  computed: {
    ...mapState(useCatalog, [
      "id",
      "animalId",
      "commentFlags",
      "commentAll",
      "page",
      "commentPages",
      "role",
      "commentNums",
    ]),

    loadComments() {
      if (this.commentFlags[this.animalId]) {
        this.fetchComments();
      }
    },
  },
  methods: {
    ...mapActions(useCatalog, [
      "setCommentFlags",
      "setCommentAll",
      "setPage",
      "setCommentPages",
      "formatDate",
      "scrollToTop",
      "setCommentNums",
    ]),

    validatePhone() {
      const phone = this.comment_text.comment_phone.trim();
      const phoneRegex = /^[\d+\-\s()]{5,20}$/;

      if (!phone) {
        this.errors.phone = "Телефон обязателен";
      } else if (!phoneRegex.test(phone)) {
        this.errors.phone = "Неверный формат телефона";
      } else {
        this.errors.phone = "";
      }
    },

    validateComment() {
      const comment = this.comment_text.comment_text.trim();
      if (!comment) {
        this.errors.comment = "Комментарий не может быть пустым";
      } else if (comment.length > 1000) {
        this.errors.comment = "Более 1000 символов в комментарии.";
      } else {
        this.errors.comment = "";
      }
    },

    async fetchComments() {
      await HomeDataService.getComments(
        this.animalId,
        this.commentPages[this.animalId]
      )
        .then((response) => {
          let existingComments = this.commentAll[this.animalId] || [];
          let newComments = response.data;

          this.setCommentAll({
            ...this.commentAll,
            [this.animalId]: [...existingComments, ...newComments],
          });
        })
        .catch((error) => {
          console.error("Ошибка при получении комментариев:", error);
        });
    },

    async deleteComment(animal_id) {
      await HomeDataService.deleteComment(animal_id)
        .then((response) => {
          this.setCommentPages({
            ...this.commentPages,
            [this.animalId]: 1,
          });
          this.setCommentAll({
            ...this.commentAll,
            [this.animalId]: [],
          });
          this.fetchComments();
          this.getCommentNum();
          console.log(response.data);
        })
        .catch((error) => {
          console.error("Ошибка при получении комментариев:", error);
        });
    },

    async fetchMoreComments() {
      this.moreCommentsFlag = !this.moreCommentsFlag;
      this.setCommentPages({
        ...this.commentPages,
        [this.animalId]: this.commentPages[this.animalId] + 1,
      });
      await this.fetchComments();
    },

    async submitComment() {
      this.validateComment();
      this.validatePhone();

      if (!this.errors.comment && !this.errors.phone) {
        try {
          await HomeDataService.createComment(
            this.id,
            this.animalId,
            this.comment_text
          );
          this.setCommentPages({
            ...this.commentPages,
            [this.animalId]: 1,
          });
          this.setCommentAll({
            ...this.commentAll,
            [this.animalId]: [],
          });
          this.fetchComments();
          this.getCommentNum();
          this.sentFlag = true;
          this.comment_text.comment_text = "";
          this.comment_text.comment_phone = "";
        } catch (e) {
          console.error(e);
        }
      }
    },

    async getCommentNum() {
      try {
        await HomeDataService.getCommentNum(this.animalId).then((response) => {
          const commentNum = {
            ...this.commentNums,
            [this.animalId]: response.data,
          };
          this.setCommentNums(commentNum);
          console.log("getCommentNum", response.data);
        });
      } catch (e) {
        console.log("e", e);
      }
    },
  },
};
</script>

<style>
.title {
  flex-grow: 1;
  padding: 2em;
}

.title h2 {
  font-weight: 900;
  font-size: 1.5em;
}

.title_comment {
  border-top: solid 1px rgba(160, 160, 160, 0.3);
  flex-grow: 1;
  padding: 0 2em 0 2em;
}

.leave_comment {
  margin-bottom: 20px;
  align-items: center;
  justify-content: center;
  display: flex;
  width: 70%;
  height: 50px;
  border-radius: 10px;
  caret-color: rgba(160, 160, 160, 0.3);
  background-color: white;
  border-width: 1px;
  padding-top: 10px;
  padding-left: 10px;
  text-align: start;
  caret-color: rgb(0, 0, 0);
}

.leave_comment:focus::placeholder {
  color: transparent;
}

.put_comm_btn {
  background-color: white;
  border: 1px solid black;
  font-size: 14px;
}

.comment_bar {
  padding: 0 2em 0 2em;
  white-space: normal;
}

.comment_more {
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.state {
  cursor: default;
  list-style: none;
  padding: 0;
}

.state li {
  display: inline-block;
  font-family: "Raleway", Helvetica, sans-serif;
  font-size: 0.6em;
  font-weight: 400;
  letter-spacing: 0.25em;
  line-height: 2;
  margin: 0 0 0 2em;
  padding: 0 0 0 2em;
  text-transform: uppercase;
}

.state li .icon {
  border-bottom: 0;
}

.state li .icon:before {
  color: rgba(160, 160, 160, 0.3);
  margin-right: 0.75em;
}

.nostyle {
  border: none;
  padding: 0;
  background: none;
  display: inline-block;
  font-family: "Raleway", Helvetica, sans-serif;
  font-weight: 400;
  letter-spacing: 0.25em;
  line-height: 2;
  text-transform: uppercase;
  color: #738392;
}

.comment_date {
  font-size: 0.6em;
  text-align: right;
}

.comment_trash {
  margin: 15px 0 15px 0;
  word-wrap: break-word;
  max-width: 100%;
  white-space: pre-wrap;
  padding: 10px;
}
</style>
