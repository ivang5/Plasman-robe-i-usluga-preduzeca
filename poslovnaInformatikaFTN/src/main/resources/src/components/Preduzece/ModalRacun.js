import React, { Component } from "react";
import styled from "styled-components";

export default class ModalRacun extends Component {
  render() {
    const { racun, deleteRacun } = this.props;
    const racunText = racun.nazivBanke + "(" + racun.brojRacuna + ")";
    return (
      <InputWrapper className="input-group form-group">
        <input
          type="text"
          className="form-control margin-left-8 max-height-6"
          defaultValue={racunText}
          readOnly
        />
        <button className="btn-delete ml-2" onClick={deleteRacun}>
          <i className="fas fa-trash" />
        </button>
      </InputWrapper>
    );
  }
}

const InputWrapper = styled.div`
  .btn-delete {
    color: var(--mainDark);
    width: 2.4rem;
    height: 2.4rem;
    background-color: var(--lightRed);
    border-radius: 50%;
  }
  .btn-delete: hover {
    background-color: var(--mediumRed);
  }
`;
