import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import ModalRacun from "./ModalRacun";

export default class DeleteRacunModal extends Component {
  render() {
    const { modalDeleteRacun, deleteRacun, closeModal, racuni } = this.props;
    if (!modalDeleteRacun) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto col-md-6 col-lg-6 text-center text-capitalize p-5"
              >
                <h3>Brisanje računa</h3>
                <hr />
                <br />
                {racuni.map((racun) => {
                  return (
                    <ModalRacun
                      key={racun.id}
                      racun={racun}
                      deleteRacun={() => deleteRacun(racun.id)}
                    />
                  );
                })}
                <Link to="/informacije">
                  <button
                    className="btn btn-primary m-4 px-3"
                    onClick={closeModal}
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
`;
