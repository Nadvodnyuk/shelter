<template>
  <div>
    <ul class="nav_bar">
      <li class="nb_li">
        <router-link v-if="token" :to="{ name: 'applicationsPage' }" class="nb_new">
          Отправленные заявки
        </router-link>
      </li>
    </ul>
    <ul class="nav_bar">
      <li class="nb_li">
        <router-link v-if="!token" :to="{ name: 'loginPage' }" class="nb_link">
          Войти
        </router-link>
      </li>
      <li class="nb_li">
        <button v-if="token" class="nb_li" @click="logoutFoo" type="button">
          Выйти
        </button>
      </li>
    </ul>
  </div>
</template>

<script>
import HomeDataService from "../../services/HomeDataService";
import { useCatalog } from "../../store/catalog.js";
import { mapState, mapActions } from "pinia";

export default {
  name: "TheNav",
  data() {
    return {
      whoami: "",
    };
  },
  computed: {
    ...mapState(useCatalog, ["token"]),
  },
  methods: {
    ...mapActions(useCatalog, [
      "setName",
      "setToken",
      "setRole",
      "setAnimalAll",
    ]),
    async logoutFoo() {
      try {
        const response = await HomeDataService.logout();
        console.log(response);
        sessionStorage.removeItem("token");
        sessionStorage.removeItem("name");
        sessionStorage.removeItem("role");
        sessionStorage.removeItem("id");
        this.setName("");
        this.setToken("");
        this.setRole("");
        sessionStorage.clear();
        await this.getAll();
        await this.$router.push("/");
        window.location.reload();
      } catch (e) {
        this.error = "Не прошло!";
      }
    },

    async getAll() {
      try {
        await HomeDataService.getAll().then((response) => {
          console.log(response.data);
          this.setAnimalAll(response.data);
        });
      } catch (e) {
        this.error = "Проверьте все поля!";
      }
    },

    async retrieveMe() {
      await HomeDataService.me()
        .then((response) => {
          this.whoami = response.data;
          console.log(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
    },
  },
};
</script>

<style scoped lang="css">
div {
  display: flex;
  align-items: center;
}
.nav_bar {
  list-style: none;
}

.nb_new {
  color: #000000;
  margin-left: 20px;
}

.nb_link {
  color: #464d4e;
  font-weight: 100;
  font-size: 18px;
  line-height: 36px;
  text-align: end;
  text-decoration: none;
  margin-left: 40px;
}

.nb_link:hover {
  color: rgb(88, 93, 99);
}

.nb_li {
  background-color: white;
}

ul {
  margin-bottom: 0;
}

@media (min-width: 300px) and (max-width: 600px) {
  .nb_link {
    color: #464d4e;
    font-weight: 600;
    font-size: 18px;
    line-height: 36px;
    text-align: end;
    text-decoration: none;
    margin-left: 15px;
    line-height: 2.2;
  }
}
</style>
