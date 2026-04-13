"use client";

import { Form, Input, Modal, Select } from "antd";
import { useEffect } from "react";
import type {
  ApartmentResponse,
  CommonRoomResponse,
  CommonRoomType,
  UpdateApartmentRequest,
  UpdateCommonRoomRequest,
} from "../types/building";
import { COMMON_ROOM_OPTIONS } from "../apiHelpers/api";

type EditTarget =
  | {
      kind: "apartment";
      data: ApartmentResponse;
    }
  | {
      kind: "commonRoom";
      data: CommonRoomResponse;
    }
  | null;

interface EditRoomModalProps {
  open: boolean;
  loading?: boolean;
  target: EditTarget;
  onCancel: () => void;
  onUpdateApartment: (
    roomId: number,
    payload: UpdateApartmentRequest,
  ) => Promise<void>;
  onUpdateCommonRoom: (
    roomId: number,
    payload: UpdateCommonRoomRequest,
  ) => Promise<void>;
}

interface EditRoomFormValues {
  ownersName?: string;
  unitNumber?: string;
  roomType?: CommonRoomType;
}

export default function EditRoomModal({
  open,
  loading = false,
  target,
  onCancel,
  onUpdateApartment,
  onUpdateCommonRoom,
}: EditRoomModalProps) {
  const [form] = Form.useForm<EditRoomFormValues>();

  useEffect(() => {
    if (!target) {
      form.resetFields();
      return;
    }

    if (target.kind === "apartment") {
      form.setFieldsValue({
        ownersName: target.data.ownersName,
        unitNumber: target.data.unitNumber,
      });
      return;
    }

    form.setFieldsValue({
      roomType: target.data.roomType,
    });
  }, [target, form]);

  async function handleOk() {
    if (!target) {
      return;
    }

    const values = await form.validateFields();

    if (target.kind === "apartment") {
      await onUpdateApartment(target.data.room.id, {
        ownersName: values.ownersName!.trim(),
        unitNumber: values.unitNumber!.trim(),
      });
    } else {
      await onUpdateCommonRoom(target.data.room.id, {
        roomType: values.roomType!,
      });
    }

    form.resetFields();
  }

  function handleCancel() {
    form.resetFields();
    onCancel();
  }

  return (
    <Modal
      title={
        target?.kind === "apartment" ? "Edit Apartment" : "Edit Common Room"
      }
      open={open}
      onCancel={handleCancel}
      onOk={() => void handleOk()}
      okText="Save"
      confirmLoading={loading}
      destroyOnHidden
    >
      {!target ? null : (
        <Form form={form} layout="vertical">
          {target.kind === "apartment" ? (
            <>
              <Form.Item
                label="Unit Number"
                name="unitNumber"
                rules={[
                  { required: true, message: "Please enter the unit number" },
                  { whitespace: true, message: "Unit number cannot be empty" },
                ]}
              >
                <Input placeholder="e.g. 101" />
              </Form.Item>

              <Form.Item
                label="Owner's Name"
                name="ownersName"
                rules={[
                  { required: true, message: "Please enter the owner's name" },
                  { whitespace: true, message: "Owner's name cannot be empty" },
                ]}
              >
                <Input placeholder="e.g. Jane Doe" />
              </Form.Item>
            </>
          ) : (
            <Form.Item
              label="Common Room Type"
              name="roomType"
              rules={[{ required: true, message: "Please select a room type" }]}
            >
              <Select
                options={COMMON_ROOM_OPTIONS.map((option) => ({
                  label: option,
                  value: option,
                }))}
              />
            </Form.Item>
          )}
        </Form>
      )}
    </Modal>
  );
}
