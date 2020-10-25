import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class GenerisanjeFaktureModal extends Component {
  state = {
    pozivNaBroj: "",
  };

  handlePozivNaBrojChange = (e) => {
    this.setState({
      pozivNaBroj: e.target.value,
    });
  };

  render() {
    const { fakGenModalOpen, closeFakGenModal, generisiFakturu } = this.props;
    if (!fakGenModalOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-6 mx-auto col-md-5 text-center text-capitalize p-5"
              >
                <h3>Generisanje fakture</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  poziv na broj: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    onChange={this.handlePozivNaBrojChange}
                  ></input>
                </h5>

                <Link to="/knjiga-predracuna">
                  <button
                    className="btn btn-primary my-btn mx-3"
                    onClick={() => {
                      generisiFakturu(this.state.pozivNaBroj);
                      closeFakGenModal();
                    }}
                  >
                    Generiši
                  </button>
                </Link>
                <Link to="/knjiga-predracuna">
                  <button
                    className="btn btn-danger my-btn mx-3"
                    onClick={closeFakGenModal}
                  >
                    Izađi
                  </button>
                </Link>
              </div>
            </div>
          </div>
        </ModalContainer>
      );
    }
  }
}

const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  #modal {
    background: var(--mainWhite);
  }
  .my-btn {
    width: 6rem;
    height: 3rem;
    margin-top: 3rem;
  }
`;
