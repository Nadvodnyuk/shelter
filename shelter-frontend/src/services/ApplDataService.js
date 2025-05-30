import http from "../http-common";

class ApplDataService {
  async getCommentsForUser(user_id) {
    return http.get(`/user/comments/showCommentsUser/${user_id}`);
  }
}

export default new ApplDataService();