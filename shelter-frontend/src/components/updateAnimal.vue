<template>
  <div>
    <button
      v-if="!isFormVisible"
      @click="openupdateAnimalForm"
      class="updateAnimalBtn"
    >
      РЕДАКТИРОВАТЬ СТАТЬЮ
    </button>
    <div v-if="isFormVisible" class="updateArtForm">
      <div class="updateFormHeader">
        <div class="updateArtTitle">
          <h2>Форма редактирования объявления</h2>
        </div>
        <div>
          <button @click="closeupdateAnimalForm" class="closeBtn">✖</button>
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
        <button @click="updateAnimalAndCloseForm" class="publishAnimalBtn">
          Сохранить изменения
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
      isAnimalupdated: false,
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
    ...mapState(useCatalog, ["animalId", "theme"]),
    formattedThemes() {
      this.themes = this.theme[0];
      return this.themes.map((t) => ({ value: t, label: t }));
    },
  },

  methods: {
    ...mapActions(useCatalog, ["setAnimalAll"]),

    validateForm() {
      let isValid = true;
      this.validationErrors = {
        title: "",
        tags: "",
        content: "",
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

      if (!this.animal.image_url.trim()) {
        this.validationErrors.image_url = "Пожалуйста, выберите изображение";
        isValid = false;
      }

      return isValid;
    },

    openupdateAnimalForm() {
      this.getAnimal();
      this.isFormVisible = true;
    },
    handleTopicChange(value) {
      console.log("value update", value);
      this.animal.topics = value;
      console.log("this.animal.topics update", this.animal.topics);
    },
    closeupdateAnimalForm() {
      this.isFormVisible = false;
      this.validationErrors = "";
    },
    handleFileUpload(event) {
      this.animal.image_url = event.target.files[0];
    },
    updateAnimalAndCloseForm() {
      if (!this.validateForm()) {
        return;
      }
      this.updateAnimal();
      this.isFormVisible = false;
      this.isAnimalupdated = true;
      this.getAnimal();
    },
    async getAnimal() {
      try {
        await HomeDataService.getAnimal(this.animalId).then((response) => {
          console.log("one", response.data);
          this.animal.title = response.data.title;
          this.animal.animal_text = response.data.animal_text;
          this.animal.topics = response.data.topics;
          console.log("проверкаааа", this.animal);
          this.animal.image_url = response.data.image_url;
        });
      } catch (e) {
        this.error = "Проверьте все поля!";
      }
    },
    async getAll() {
      try {
        await HomeDataService.getAll().then((response) => {
          console.log("getAll", response.data);
          this.setAnimalAll(response.data);
        });
      } catch (e) {
        this.error = "Проверьте все поля!";
      }
    },
    async updateAnimal() {
      try {
        await HomeDataService.updateAnimal(this.animalId, this.animal).then(
          (response) => {
            console.log("updateAnimal", response.data);
            this.animal.title = response.data.title;
            this.animal.animal_text = response.data.animal_text;
            this.animal.topics = response.data.topics;
            this.animal.image_url = response.data.image_url;
            this.getAll();
          }
        );
      } catch (e) {
        this.error = "Проверьте все поля!";
      }
    },
  },
  watch: {
    animalId(newVal) {
      this.getAnimal();
    },
  },
};
</script>

<style>
.updateAnimalBtn {
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

.updateArtTitle {
  margin-right: 175px;
}

.updateFormHeader {
  display: flex;
  justify-content: right;
}

.closeBtn {
  background-color: white;
  width: 30px;
  align-items: center;
  float: right;
  /* Помещаем кнопку вправо */
  margin-right: 0px;
  text-align: center;
  padding-right: 30px;
  padding-top: 10px;
}

.updateArtForm {
  display: flex;
  flex-direction: column;
  width: 800px;
  margin: 0 auto;
  height: auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
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
  resize: vertical;
  /* Разрешает вертикальное изменение размера поля */
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
