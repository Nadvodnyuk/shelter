<script setup>
import updateAnimal from "../../components/updateAnimal.vue";
import comments from "../../components/comments.vue";
</script>

<template>
  <div class="sub_header"></div>
  <div class="news_page">
    <article
      class="post"
      v-for="animal in animalsWithComments"
      :key="animal.animal_id"
    >
      <header>
        <div class="title">
          <h2>
            {{ animal.title }}, {{ getAgeText(animal.age) }},
            {{ animal.breed }},
            {{ getGenderText(animal.gender) }}
          </h2>
          <p>
            {{ animal.topics }}
          </p>
        </div>
        <div class="meta">
          <p class="published">
            {{ formatDate(animal.publicationDate) }}
          </p>
        </div>
      </header>
      <div class="art">
        <a class="image_main">
          <img :src="animal.image_url" alt="" />
        </a>
        <p class="animal_text">
          {{
            animal.animal_text.length > 100 &&
            animal.animal_id !== expandedAnimalId
              ? animal.animal_text.slice(0, 100) + "..."
              : animal.animal_text
          }}
        </p>
      </div>
      <footer>
        <ul class="stats">
          <li v-if="role === 'ROLE_ADMIN'">
            <button
              class="unstyled-button"
              @click="deleteAnimal(animal.animal_id)"
            >
              Удалить статью
            </button>
          </li>
          <li>
            <updateAnimal
              v-if="role === 'ROLE_ADMIN'"
              class="updateAnimalBtn"
              @click="setAnimalId(animal.animal_id)"
            />
          </li>
          <li>
            <button
              class="unstyled-button"
              @click="toggleLikedFlag(animal.animal_id)"
            >
              <img
                class="mini"
                src="/img/heart.svg"
                alt="mini like"
                v-show="!likedFlags[animal.animal_id]"
              />
              <img
                class="mini"
                src="/img/blue-heart.svg"
                alt="mini like"
                v-show="likedFlags[animal.animal_id]"
              />
            </button>
            <div class="unstyled-button">
              {{ likeNums[animal.animal_id] }}
            </div>
          </li>
          <li>
            <button
              class="unstyled-button"
              @click="commenting(animal.animal_id)"
            >
              <img
                class="mini"
                src="/img/comment2.svg"
                alt="mini like"
                v-show="!commentFlags[animal.animal_id]"
              />
              <img
                class="mini"
                src="/img/comment-open.svg"
                alt="mini like"
                v-show="commentFlags[animal.animal_id]"
              />
            </button>
            <button
              class="unstyled-button"
              @click="
                commentFlag[animal.animal_id] = !commentFlag[animal.animal_id];
                setCommentFlags(commentFlag);
              "
            >
              {{ commentNums[animal.animal_id] }}
            </button>
          </li>
          <li>
            <button
              class="unstyled-button"
              @click="toggleExpandedAnimal(animal.animal_id)"
            >
              {{
                animal.animal_id === expandedAnimalId
                  ? "Показать меньше"
                  : "Показать больше"
              }}
            </button>
          </li>
        </ul>
      </footer>
      <div v-show="commentFlags[animal.animal_id]">
        <comments />
      </div>
    </article>
    <div class="space"></div>
  </div>
</template>

<script>
import { useCatalog } from "../../store/catalog.js";
import { mapState, mapActions } from "pinia";
import HomeDataService from "../../services/HomeDataService";

export default {
  name: "applicationsPage",
  data() {
    return {
      size: 30,
      picture: "",
      date: "",
      time: "",
      myNewComment: "",
      likeFlag: false,
      expandedAnimalId: null,
      moreCommentsFlag: false,
      sentFlag: false,
      likedFlag: false,
      errors: {
        comment: "",
      },
      like: {
        userL: "",
        animalL: "",
      },
      likedFlags: {},
      likeNum: {},
      commentNum: {},
      commentFlag: {},
    };
  },
  computed: {
    ...mapState(useCatalog, [
      "role",
      "animalAll",
      "id",
      "page",
      "likeNums",
      "commentNums",
      "commentFlags",
      "commentAll",
      "commentPages",
      "animalId",
    ]),

    animalsWithComments() {
      return this.animalAll.filter(
        (animal) => this.commentNums[animal.animal_id] > 0
      );
    },

    async get() {
      try {
        await this.getAllThemes();
        await HomeDataService.getAll().then((response) => {
          let pages = {};
          let comment = {};

          this.setAnimalAll(response.data);

          this.likedFlags = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, false])
          );
          this.likeNum = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, 0])
          );
          this.commentNum = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, 0])
          );
          this.commentFlag = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, false])
          );
          comment = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, []])
          );
          pages = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, 1])
          );

          response.data.forEach((animal) => {
            this.getLikeNum(animal.animal_id);
            this.getCommentNum(animal.animal_id);
            this.isLiked(this.id, animal.animal_id);
          });

          this.setLikeNums(this.likeNum);
          this.setCommentNums(this.commentNum);
          this.setCommentFlags(this.commentFlag);
          this.setCommentAll(comment);
          this.setCommentPages(pages);
          console.log("помогите");
        });
      } catch (e) {
        console.log("e", e);
      }
    },
  },
  methods: {
    ...mapActions(useCatalog, [
      "setAnimalAll",
      "setAnimalId",
      "setLikeNums",
      "setCommentNums",
      "setCommentFlags",
      "setCommentAll",
      "setCommentPages",
      "formatDate",
      "scrollToTop",
      "setTheme",
    ]),

    getGenderText(gender) {
      return gender === "MALE" ? "мальчик" : "девочка";
    },

    getAgeText(age) {
      const lastDigit = age % 10;
      const lastTwoDigits = age % 100;

      if (lastTwoDigits >= 11 && lastTwoDigits <= 14) {
        return `${age} лет`;
      }

      if (lastDigit === 1) return `${age} год`;
      if (lastDigit >= 2 && lastDigit <= 4) return `${age} года`;

      return `${age} лет`;
    },

    toggleExpandedAnimal(animalId) {
      if (this.expandedAnimalId === animalId) {
        this.expandedAnimalId = null;
      } else {
        this.expandedAnimalId = animalId;
      }
    },

    async getAll() {
      try {
        await this.getAllThemes();
        await HomeDataService.getAll().then((response) => {
          let pages = {};
          let comment = {};

          this.setAnimalAll(response.data);

          this.likedFlags = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, false])
          );
          this.likeNum = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, 0])
          );
          this.commentNum = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, 0])
          );
          this.commentFlag = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, false])
          );
          comment = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, []])
          );
          pages = Object.fromEntries(
            response.data.map((animal) => [animal.animal_id, 1])
          );

          response.data.forEach((animal) => {
            this.getLikeNum(animal.animal_id);
            this.getCommentNum(animal.animal_id);
            this.isLiked(this.id, animal.animal_id);
          });

          this.setLikeNums(this.likeNum);
          this.setCommentNums(this.commentNum);
          this.setCommentFlags(this.commentFlag);
          this.setCommentAll(comment);
          this.setCommentPages(pages);
        });
      } catch (e) {
        console.log("e", e);
      }
    },

    async deleteAnimal(id) {
      try {
        await HomeDataService.deleteAnimal(id).then((response) => {
          console.log("deleteAnimal", response.data);
          this.getAll();
        });
      } catch (e) {
        console.log("e", e);
      }
    },

    async toggleLikedFlag(animalId) {
      try {
        const currentState = this.likedFlags[animalId];
        this.likedFlags[animalId] = !currentState;
        const user_id = this.id;
        if (this.likedFlags[animalId]) {
          await this.liking(user_id, animalId);
        } else {
          await HomeDataService.deleteLike(user_id, animalId).then(
            (response) => {
              console.log("deleteLike", response.data);
            }
          );
        }
        this.getLikeNum(animalId);
        this.setLikeNums(this.likeNum);
      } catch (e) {
        console.log("e", e);
      }
    },

    async getLikeNum(animalId) {
      try {
        await HomeDataService.getLikeNum(animalId).then((response) => {
          this.likeNum[animalId] = response.data;
        });
        this.setLikeNums(this.likeNum);
      } catch (e) {
        console.log("e", e);
      }
    },

    async getCommentNum(animalId) {
      try {
        await HomeDataService.getCommentNum(animalId).then((response) => {
          this.commentNum[animalId] = response.data;
        });
        this.setCommentNums(this.commentNum);
      } catch (e) {
        console.log("e", e);
      }
    },

    async isLiked(id, animalId) {
      if (id) {
        try {
          await HomeDataService.isLiked(id, animalId).then((response) => {
            this.likedFlags[animalId] = response.data;
          });
        } catch (e) {
          console.log("e", e);
        }
      }
    },

    async liking(user_id, animal_id) {
      try {
        console.log("user_id, animal_id", user_id, animal_id);
        let like = {
          userL: user_id,
          animalL: animal_id,
        };
        await HomeDataService.createLike(like).then((response) => {
          console.log("createLike", response.data);
        });
      } catch (e) {
        console.log("e", e);
      }
    },

    async commenting(animal_id) {
      try {
        this.commentFlag[animal_id] = !this.commentFlag[animal_id];
        this.setCommentFlags(this.commentFlag);
        this.setAnimalId(animal_id);
        await this.fetchComments(animal_id);
        console.log("this.commentAll", this.commentAll);
      } catch (e) {
        console.log("e", e);
      }
    },

    async getAllThemes() {
      try {
        await HomeDataService.getAllThemes().then((response) => {
          console.log("getAllThemes", response.data);
          this.setTheme(response.data);
        });
      } catch (e) {
        this.error = "Проверьте все поля!";
      }
    },

    async fetchComments(animal_id) {
      await HomeDataService.getComments(animal_id, this.commentPages[animal_id])
        .then((response) => {
          let comments = {};

          response.data.forEach((comment) => {
            if (comments[animal_id]) {
              comments[animal_id].push(comment);
            } else {
              comments[animal_id] = [comment];
            }
          });
          this.setCommentAll(comments);
        })
        .catch((error) => {
          console.error("Ошибка при получении комментариев:", error);
        });
    },
  },

  async created() {
    await this.getAll();
    await this.getAllThemes();
    console.log("this.animalAll", this.animalAll);
  },
};
</script>

<style scoped src="./application.css"></style>
