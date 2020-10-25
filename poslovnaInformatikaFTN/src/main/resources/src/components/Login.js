import React, { Component } from "react";
import styled from "styled-components";
import history from "../history";

export default class Login extends Component {
  state = {
    username: "",
    password: "",
    loginUrl: "http://localhost:8080/api/login",
  };

  login = (e) => {
    e.preventDefault();
    const data = {
      username: this.state.username,
      password: this.state.password,
    };
    fetch(this.state.loginUrl, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    })
      .then(function (response) {
        return response.text();
      })
      .then(function (text) {
        sessionStorage.setItem("token", JSON.parse(text).token);
        sessionStorage.setItem(
          "podaci",
          JSON.stringify(JSON.parse(text).podaci)
        );
        history.push("/proizvodi");
      })
      .catch((error) => {
        console.error("Error: ", error);
      });
  };

  handleUsernameChange = (e) => {
    this.setState({
      username: e.target.value,
    });
  };

  handlePasswordChange = (e) => {
    this.setState({
      password: e.target.value,
    });
  };

  render() {
    return (
      <ContainerWrapper className="container-flex">
        <div className="d-flex justify-content-center h-100">
          <div className="card">
            <div className="card-header">
              <h3>Prijavi se</h3>
            </div>
            <div className="card-body">
              <form>
                <div className="input-group form-group">
                  <div className="input-group-prepend">
                    <span className="input-group-text">
                      <i className="fas fa-user"></i>
                    </span>
                  </div>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="korisničko ime"
                    onChange={this.handleUsernameChange}
                  />
                </div>
                <div className="input-group form-group">
                  <div className="input-group-prepend">
                    <span className="input-group-text">
                      <i className="fas fa-key"></i>
                    </span>
                  </div>
                  <input
                    type="password"
                    className="form-control"
                    placeholder="lozinka"
                    onChange={this.handlePasswordChange}
                  />
                </div>
                <div className="form-group">
                  <input
                    type="submit"
                    value="Prijava"
                    className="btn float-right login_btn"
                    onClick={this.login}
                  />
                </div>
              </form>
            </div>
          </div>
        </div>
      </ContainerWrapper>
    );
  }
}

const ContainerWrapper = styled.div`
  height: 100%;
  align-content: center;
  background-image: url("https://cdn.discordapp.com/attachments/525301926309396493/740522212817109042/wp3115786.png");
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  .card {
    height: 18rem;
    margin-top: auto;
    margin-bottom: auto;
    width: 25rem;
    background-color: rgba(0, 0, 0, 0.8) !important;
  }
  .card-header {
    color: var(--mainWhite);
  }
  .login_btn {
    margin-top: 1rem;
    color: black;
    background-color: var(--mainOrange);
    width: 6rem;
  }
  .login_btn: hover {
    background-color: var(--lightOrange);
  }
  .input-group-prepend span {
    background-color: var(--mainOrange);
    color: var(--mainDark);
    border: 0 !important;
  }
`;
