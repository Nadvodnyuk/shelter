import { defineStore } from "pinia";

export const useCatalog = defineStore("catalog-store", {
  id: "news",
  state: () => {
    return {
      id: sessionStorage.getItem("id") || "",
      name: sessionStorage.getItem("name") || "",
      token: sessionStorage.getItem("token") || "",
      role: sessionStorage.getItem("role") || "",
      favorite: [],
      blocked: [],
      animalId: "",
      animalAll: {},
      likeNums: {},
      commentNums: {},
      commentFlags: {},
      commentAll: {},
      page: 1,
      commentPages: {},
      theme: [],
    };
  },

  actions: {
    setId(id) {
      this.id = id;
      sessionStorage.setItem("id", id);
    },

    setName(name) {
      this.name = name;
      sessionStorage.setItem("name", name);
    },

    setToken(token) {
      this.token = token;
      sessionStorage.setItem("token", token);
    },

    setRole(role) {
      this.role = role;
      sessionStorage.setItem("role", role);
    },

    setFavorite(favorite) {
      this.favorite = favorite;
    },

    setBlocked(blocked) {
      this.blocked = blocked;
    },

    setAnimalId(animalId) {
      this.animalId = animalId;
    },

    setAnimalAll(animalAll) {
      this.animalAll = animalAll;
      //.reverse()
    },

    setTheme(theme) {
      this.theme = Object.values(theme);
    },

    setLikeNums(likeNums) {
      this.likeNums = likeNums;
    },

    setCommentNums(commentNums) {
      this.commentNums = commentNums;
    },

    setCommentFlags(commentFlags) {
      this.commentFlags = commentFlags;
    },

    setCommentAll(commentAll) {
      this.commentAll = commentAll;
    },

    setPage(page) {
      this.page = page;
    },

    setCommentPages(commentPages) {
      this.commentPages = commentPages;
    },

    formatDate(dateString) {
      const date = new Date(dateString);
      return date.toLocaleString("ru-RU", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
        hour12: false,
      });
    },

    scrollToTop() {
      window.scrollTo(0, 0);
    },
  },
});
