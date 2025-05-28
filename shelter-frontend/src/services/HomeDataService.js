import http from "../http-common";

class HomeDataService {
  async logout() {
    return http.post("/both/users/log_out");
  }
  async me() {
    return http.get("/auth/users/me");
  }
  async deleteUser(id) {
    //admin
    return http.delete(`/admin/users/${id}`);
  }

  async getAll() {
    return http.get("/auth/animals/all");
  }
  async createAnimal(data) {
    //admin
    return http.post("/admin/animals/create", data);
  }
  async getAnimal(id) {
    //admin
    return http.get(`/admin/animals/${id}`);
  }
  async updateAnimal(id, data) {
    //admin
    return http.put(`/admin/animals/update/${id}`, data);
  }
  async deleteAnimal(id) {
    //admin
    return http.delete(`/admin/animals/${id}`);
  }

  async getAllThemes() {
    return http.get("/both/themes");
  }
  async saveTopics(topics) {
    //user
    return http.post("/user/themes", topics);
  }

  async getCommentNum(animal_id) {
    return http.post(`/auth/comments/commentNum?animal_id=${animal_id}`);
  }
  async getComments(animal_id, page) {
    return http.get(`/auth/comments/showComments/${animal_id}/${page}`);
  }
  async createComment(user_id, animal_id, data) {
    //user
    return http.post(
      `/both/comments/postComment?user_id=${user_id}&animal_id=${animal_id}`,
      data
    );
  }
  async deleteComment(id) {
    //admin
    return http.delete(`/admin/comments/${id}`);
  }

  async getLikeNum(animal_id) {
    return http.post(`/auth/likes/likeNum?animal_id=${animal_id}`);
  }

  async isLiked(user_id, animal_id) {
    return http.post(
      `/auth/likes/isLiked?user_id=${user_id}&animal_id=${animal_id}`
    );
  }
  async createLike(data) {
    //user
    return http.post("/both/likes/postLike", data);
  }
  async deleteLike(userL, animalL) {
    //user
    return http.delete(`/both/likes/${userL}/${animalL}`);
  }
}

export default new HomeDataService();
