<script setup>
import TheNav from "../../components/nav/TheNav.vue";
import { useCatalog } from "../../store/catalog.js";
import { mapState, mapActions } from "pinia";
import HomeDataService from "../../services/HomeDataService";
</script>

<template>
  <header class="header">
    <div class="header_bar">
      <router-link :to="{ name: 'homePage' }">
        <div class="logo_title">
          <div>
            <a target="_blank">
              <img class="logo" src="/img/dog-house.svg" alt="Shelter logo" />
            </a>
          </div>
          <div class="title">
            <h1 class="headerTitle">Приют для животных</h1>
          </div>
        </div>
      </router-link>
      <div class="person">
        <div class="name" @click="retrieveMe()">
          {{ name }}
        </div>
        <TheNav class="nab_bar" />
      </div>
    </div>
  </header>
</template>

<script>
export default {
  name: "TheHeader",
  data() {
    return {
      localStorageKey: "name",
      whoami: this.name,
    };
  },
  computed: {
    ...mapState(useCatalog, ["name"]),
  },
  methods: {
    async retrieveMe() {
      console.log(sessionStorage.getItem("token"));
      await HomeDataService.me()
        .then((response) => {
          this.whoami = this.name;
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
.header {
  height: 80px;
  width: 100%;
  display: flex;
  align-items: center;
  background-color: #ffffff;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
}

.header_bar {
  margin-left: auto;
  margin-right: auto;
  max-width: 1440px;
  padding-left: 80px;
  padding-right: 80px;
  transition: padding var(--transition);
  width: 100%;
  display: flex;
  flex-direction: row;
  align-items: bottom;
  position: relative;
  justify-content: space-between;
}

.logo_title {
  display: flex;
  flex-direction: row;
  align-items: center;
  padding-bottom: 5px;
}

.title {
  align-items: center;
  height: 50px;
  padding-top: 0;
  color: white;
}

.headerTitle {
  font-size: 2.7em;
  line-height: 1.1;
  color: #464d4e;
}

.logo {
  will-change: filter;
  transition: filter 300ms;
  height: 50px;
  margin-top: 5px;
  margin-right: 10px;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #84daff8c);
}

.person {
  list-style: none;
  display: flex;
  justify-content: center;
  align-items: end;
  margin-bottom: 10px;
}

.name {
  color: #464d4e;
  font-weight: 100;
  font-size: 18px;
  line-height: 36px;
  text-align: end;
  text-decoration: none;
  margin-left: 40px;
}

@media (min-width: 300px) and (max-width: 600px) {
  .header_bar {
    margin-left: auto;
    margin-right: auto;
    max-width: 1440px;
    padding-left: 20px;
    padding-right: 80px;
    transition: padding var(--transition);
    width: 100%;
    display: flex;
    flex-direction: row;
    align-items: bottom;
    position: relative;
  }

  .title {
    align-items: center;
    height: 50px;
    padding-top: 5px;
    color: white;
  }

  .headerTitle {
    font-size: 2.7em;
    color: white;
  }
}
</style>
