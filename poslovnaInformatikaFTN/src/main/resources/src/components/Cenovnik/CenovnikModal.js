import React, { Component } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";

export default class CenovnikModal extends Component {
  state = {
    promenjeneStavke: [],
  };

  handleCenaChange = (id, value) => {
    var stavke = this.state.promenjeneStavke;
    var stavka = stavke.find((element) => element.id === id);
    if (stavka !== undefined) {
      var filtriraneStavke = stavke.filter(
        (element) => element.id !== stavka.id
      );
      stavka.cena = value;
      filtriraneStavke.push(stavka);
    } else {
      stavka = {
        id: id,
        cena: value,
      };
      stavke.push(stavka);
    }
  };

  render() {
    const { stavke, modalOpen, closeModal, saveModal } = this.props;
    if (!modalOpen) {
      return null;
    } else {
      return (
        <ModalContainer>
          <div className="container">
            <div className="row">
              <div
                id="modal"
                className="col-8 mx-auto col-md-6 col-lg-4 text-center text-capitalize p-5"
              >
                {stavke.map((stavka) => {
                  return (
                    <div key={stavka.id}>
                      <h5>{stavka.proizvod.naziv}</h5>
                      <div className="input-group form-group">
                        <label className="card-label mr-3 mt-1">Cena:</label>
                        <input
                          type="number"
                          className="form-control margin-left-1"
                          defaultValue={stavka.cena}
                          onChange={(event) =>
                            this.handleCenaChange(stavka.id, event.target.value)
                          }
                        />
                      </div>
                      <hr className="mt-4" />
                    </div>
                  );
                })}
                <Link to="/cenovnik">
                  <button
                    className="btn btn-success mx-4 mt-3"
                    onClick={() => saveModal(this.state.promenjeneStavke)}
                  >
                    Izmeni
                  </button>
                </Link>
                <Link to="/cenovnik">
                  <button
                    className="btn btn-danger mx-4 mt-3"
                    onClick={closeModal}
                  >
                    Odustani
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
