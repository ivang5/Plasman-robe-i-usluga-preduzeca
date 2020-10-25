import React, { Component } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

export default class PoslovniPartnerModal extends Component {
  state = {
    inputPib: "",
    inputNaziv: "",
    inputVrstaPartnera: "",
    inputRacuniUBanci: "",
    inputAdresa: "",
    inputTelefon: "",
    inputEmail: "",
  };

  handlePibChange = (e) => {
    this.setState({
      inputPib: e.target.value,
    });
  };

  handleNazivChange = (e) => {
    this.setState({
      inputNaziv: e.target.value,
    });
  };

  handleVrstaPartneraChange = (e) => {
    this.setState({
      inputVrstaPartnera: e.target.value,
    });
  };

  handleAdresaChange = (e) => {
    this.setState({
      inputAdresa: e.target.value,
    });
  };

  handleTelefonChange = (e) => {
    this.setState({
      inputTelefon: e.target.value,
    });
  };

  handleEmailChange = (e) => {
    this.setState({
      inputEmail: e.target.value,
    });
  };

  render() {
    const { modalOpen, closeModal, saveModal } = this.props;
    const {
      id,
      pib,
      naziv,
      vrstaPartnera,
      racuniUBanci,
      adresa,
      telefon,
      email,
    } = this.props.modalPoslovniPartner;

    var racuni = "";
    if (racuniUBanci !== undefined) {
      racuniUBanci.forEach((element) => {
        racuni =
          racuni === ""
            ? element.nazivBanke + "(" + element.brojRacuna + ")"
            : racuni +
              ", " +
              element.nazivBanke +
              "(" +
              element.brojRacuna +
              ")";
      });
    }

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
                  pib: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={pib}
                    onChange={this.handlePibChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  naziv: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={naziv}
                    onChange={this.handleNazivChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  vrsta partnera: <br />{" "}
                  <select
                    type="text"
                    className="form-control"
                    defaultValue={vrstaPartnera}
                    onChange={this.handleVrstaPartneraChange}
                  >
                    <option value="KUPAC">Kupac</option>
                    <option value="DOBAVLJAC">Dobavljač</option>
                    <option value="KUPAC_I_DOBAVLJAC">Kupac i dobavljač</option>
                  </select>
                </h5>
                <h5 className="mb-3">
                  racuni u banci: <br />{" "}
                  <textarea
                    type="text"
                    className="form-control"
                    defaultValue={racuni}
                  ></textarea>
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
                  broj telefona: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={telefon}
                    onChange={this.handleTelefonChange}
                  ></input>
                </h5>
                <h5 className="mb-3">
                  email: <br />{" "}
                  <input
                    type="text"
                    className="form-control"
                    defaultValue={email}
                    onChange={this.handleEmailChange}
                  ></input>
                </h5>
                <Link to="/poslovni-partneri">
                  <button
                    className="btn btn-success m-4"
                    onClick={() => {
                      saveModal(
                        id,
                        this.state.inputPib,
                        this.state.inputNaziv,
                        this.state.inputVrstaPartnera,
                        this.state.inputRacuniUBanci,
                        this.state.inputAdresa,
                        this.state.inputTelefon,
                        this.state.inputEmail
                      );
                      this.setState({
                        inputPib: "",
                        inputNaziv: "",
                        inputVrstaPartnera: "",
                        inputRacuniUBanci: "",
                        inputAdresa: "",
                        inputTelefon: "",
                        inputEmail: "",
                      });
                    }}
                  >
                    Izmeni
                  </button>
                </Link>
                <Link to="/poslovni-partneri">
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
