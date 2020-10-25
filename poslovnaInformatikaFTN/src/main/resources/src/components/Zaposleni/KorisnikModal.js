import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class KorisnikModal extends Component {
  state = {
    inputIme: "",
    inputPrezime: "",
    inputAdresa: "",
    inputBrojTelefona: "",
    inputUloga: "",
  };

  handleImeChange = (e) => {
    this.setState({
      inputIme: e.target.value,
    });
  };

  handlePrezimeChange = (e) => {
    this.setState({
      inputPrezime: e.target.value,
    });
  };

  handleAdresaChange = (e) => {
    this.setState({
      inputAdresa: e.target.value,
    });
  };

  handleBrojTelefonaChange = (e) => {
    this.setState({
      inputBrojTelefona: e.target.value,
    });
  };

  handleUlogaChange = (e) => {
    this.setState({
      inputUloga: e.target.value,
    });
  };

  render() {
    const { modalOpen, closeModal, saveModal } = this.props;
    const {
      id,
      ime,
      prezime,
      adresa,
      brojTelefona,
      uloga,
    } = this.props.modalKorisnik;

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
                <h3>izmena</h3>
                <hr />
                <br />
                <h5 className="mb-3">
                  ime: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={ime}
                    onChange={this.handleImeChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  prezime: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={prezime}
                    onChange={this.handlePrezimeChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  adresa: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={adresa}
                    onChange={this.handleAdresaChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  brojTelefona: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={brojTelefona}
                    onChange={this.handleBrojTelefonaChange}
                  ></input>
                </h5>

                <h5 className="mb-3">
                  uloga: <br />{" "}
                  <select
                    type="text"
                    className="form-control"
                    onChange={this.handleUlogaChange}
                    defaultValue={uloga}
                  >
                    <option value="GENERALNI_DIREKTOR">
                      Generalni direktor
                    </option>
                    <option value="DIREKTOR_FINANSIJA">
                      Direktor finansija
                    </option>
                    <option value="DIREKTOR_MARKETINGA">
                      Direktor marketinga
                    </option>
                    <option value="RUKOVODILAC_POSTROJENJA">
                      Rukovodilac postrojenja
                    </option>
                    <option value="OPERATER">Operater</option>
                  </select>
                </h5>
                <Link to="/zaposleni">
                  <button
                    className="btn btn-success m-4"
                    onClick={() => {
                      saveModal(
                        id,
                        this.state.inputIme,
                        this.state.inputPrezime,
                        this.state.inputAdresa,
                        this.state.inputBrojTelefona,
                        this.state.inputUloga
                      );
                      this.setState({
                        inputIme: "",
                        inputPrezime: "",
                        inputAdresa: "",
                        inputBrojTelefona: "",
                        inputUloga: "",
                      });
                    }}
                  >
                    Izmeni
                  </button>
                </Link>
                <Link to="/zaposleni">
                  <button className="btn btn-danger m-4" onClick={closeModal}>
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
