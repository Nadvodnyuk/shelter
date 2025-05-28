<template>
  <div>
    <button
      v-if="!isFormVisible"
      @click="openCreateAnimalForm"
      class="createAnimalBtn"
    >
      Создать статью
    </button>
    <div v-if="isFormVisible" class="createArtForm">
      <div class="createFormHeader">
        <div class="createArtTitle"><h2>Форма создания объявления</h2></div>
        <div>
          <button @click="closeCreateAnimalForm" class="closeBtn">✖</button>
        </div>
      </div>
      <input v-model="animal.title" placeholder="Заголовок" class="artTitle" />
      <span v-if="validationErrors.title" class="error-message">{{
        validationErrors.title
      }}</span>
      <a-select
        v-model="animal.topics"
        mode="multiple"
        style="width: 100%"
        placeholder="Теги"
        :options="formattedThemes"
        @change="handleTopicChange"
      ></a-select>
      <span v-if="validationErrors.topics" class="error-message">{{
        validationErrors.topics
      }}</span>
      <textarea
        v-model="animal.animal_text"
        placeholder="Текст объявления"
        class="artText"
      ></textarea>
      <span v-if="validationErrors.animal_text" class="error-message">{{
        validationErrors.animal_text
      }}</span>
      <div class="file-input">
        <input
          v-model="animal.image_url"
          placeholder="Изображение"
          class="artTags"
        />
        <span v-if="validationErrors.image_url" class="error-message">
          {{ validationErrors.image_url }}
        </span>
      </div>
      <div class="publishDiv">
        <button @click="createAnimalAndCloseForm()" class="publishAnimalBtn">
          Создать
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import { useCatalog } from "../store/catalog.js";
import { mapState, mapActions } from "pinia";
import HomeDataService from "../services/HomeDataService.js";

export default {
  data() {
    return {
      themes: 0,
      isFormVisible: false,
      isAnimalCreated: false,
      likeNum: {},
      commentNum: {},
      animal: {
        title: "",
        animal_text: "",
        image_url: "",
        topics: [],
      },
      validationErrors: {
        title: "",
        topics: "",
        animal_text: "",
      },
    };
  },
  computed: {
    ...mapState(useCatalog, [
      "theme",
      "likeNums",
      "commentNums",
      "commentFlags",
      "commentAll",
      "commentPages",
    ]),
    formattedThemes() {
      this.themes = this.theme[0];
      return this.themes.map((t) => ({ value: t, label: t }));
    },
  },
  methods: {
    ...mapActions(useCatalog, [
      "setAnimalAll",
      "setLikeNums",
      "setCommentNums",
      "setCommentFlags",
      "setCommentAll",
      "setCommentPages",
    ]),
    validateForm() {
      let isValid = true;
      this.validationErrors = {
        title: "",
        topics: "",
        animal_text: "",
      };

      if (!this.animal.title.trim()) {
        this.validationErrors.title = "Пожалуйста, введите заголовок объявления";
        isValid = false;
      } else if (this.animal.title.length > 255) {
        this.validationErrors.title =
          "Максимальная длина заголовка - 255 символов";
        isValid = false;
      }

      if (!this.animal.topics.length) {
        this.validationErrors.topics = "Пожалуйста, введите теги объявления";
        isValid = false;
      }

      if (!this.animal.animal_text.trim()) {
        this.validationErrors.animal_text = "Пожалуйста, введите текст объявления";
        isValid = false;
      }

      if (!this.animal.image_url) {
        this.validationErrors.image_url = "Пожалуйста, выберите изображение";
        isValid = false;
      }

      return isValid;
    },
    openCreateAnimalForm() {
      this.isFormVisible = true;
    },
    closeCreateAnimalForm() {
      this.isFormVisible = false;
      this.validationErrors = "";
    },
    handleTopicChange(value) {
      console.log("value", value);
      this.animal.topics = value;
      console.log("this.animal.topics", this.animal.topics);
    },
    handleFileUpload(event) {
      this.animal.image_url = event.target.files[0];
    },
    createAnimalAndCloseForm() {
      if (!this.validateForm()) {
        return;
      }
      this.isFormVisible = false;
      this.isAnimalCreated = true;
      this.createAnimal();
      this.animal = {
        title: "",
        animal_text: "",
        image_url: "",
        topics: [],
      };
    },
    async getAll() {
      try {
        let pages = {};
        let comment = {};
        await HomeDataService.getAll().then((response) => {
          this.setAnimalAll(response.data);
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
          });

          this.setLikeNums(this.likeNum);
          this.setCommentNums(this.commentNum);
          this.setCommentFlags(this.commentFlag);
          this.setCommentAll(comment);
          this.setCommentPages(pages);
        });
      } catch (e) {
        this.error = "Проверьте все поля!";
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

    async createAnimal() {
      try {
        console.log(this.animal.topics);
        await HomeDataService.createAnimal(this.animal).then((response) => {
          console.log(response.data);
          this.getAll();
          this.getTheme();
        });
      } catch (e) {
        this.error = "Проверьте все поля!";
      }
    },
  },
};
</script>

<style>
.createAnimalBtn {
  background-color: white;
}

.createArtTitle {
  margin-right: 295px;
}

.createFormHeader {
  display: flex;
  justify-content: right;
}

.closeBtn {
  background-color: white;
  width: 30px;
  align-items: center;
  float: right; /* Помещаем кнопку вправо */
  margin-right: 0px;
  text-align: center;
  padding-right: 30px;
  padding-top: 10px;
}

.createArtForm {
  display: flex;
  flex-direction: column;
  width: 1000px;
  margin: 0 auto;
  height: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}

.artTitle {
  margin-bottom: 20px;
}

.artTitle,
.artTags,
.artText,
.artImg {
  width: 100%;
  height: 10px;
  padding: 20px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 20px;
  margin-top: 20px;
}

.artText {
  overflow: hidden;
  resize: vertical; /* Разрешает вертикальное изменение размера поля */
  overflow-y: auto;
  border-radius: 5px;
  min-height: 50px;
  height: auto;
}

.artImg {
  height: 65px;
  position: relative;
  overflow: hidden;
  display: inline-block;
}

.file-input {
  align-items: center;
}

.success-message {
  color: green;
  margin-top: 10px;
}

.error-message {
  color: red;
  margin-top: 1px;
  margin-left: 21px;
  margin-bottom: 5px;
}

.publishDiv {
  display: flex;
  justify-content: center;
}

.publishAnimalBtn {
  margin-top: 20px;
  background-color: white;
  width: 105px;
}
</style>
