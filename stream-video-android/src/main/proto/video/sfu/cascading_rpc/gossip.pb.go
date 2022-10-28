// Code generated by protoc-gen-go. DO NOT EDIT.
// versions:
// 	protoc-gen-go v1.28.0
// 	protoc        v3.21.5
// source: video/sfu/cascading_rpc/gossip.proto

package sfu_gossip_rpc

import (
	protoreflect "google.golang.org/protobuf/reflect/protoreflect"
	protoimpl "google.golang.org/protobuf/runtime/protoimpl"
	reflect "reflect"
	sync "sync"
)

const (
	// Verify that this generated code is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(20 - protoimpl.MinVersion)
	// Verify that runtime/protoimpl is sufficiently up-to-date.
	_ = protoimpl.EnforceVersion(protoimpl.MaxVersion - 20)
)

type Subscription struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	PublisherId string `protobuf:"bytes,1,opt,name=publisher_id,json=publisherId,proto3" json:"publisher_id,omitempty"`
	Rid         string `protobuf:"bytes,2,opt,name=rid,proto3" json:"rid,omitempty"`
	Coded       string `protobuf:"bytes,3,opt,name=coded,proto3" json:"coded,omitempty"`
}

func (x *Subscription) Reset() {
	*x = Subscription{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[0]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Subscription) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Subscription) ProtoMessage() {}

func (x *Subscription) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[0]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Subscription.ProtoReflect.Descriptor instead.
func (*Subscription) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{0}
}

func (x *Subscription) GetPublisherId() string {
	if x != nil {
		return x.PublisherId
	}
	return ""
}

func (x *Subscription) GetRid() string {
	if x != nil {
		return x.Rid
	}
	return ""
}

func (x *Subscription) GetCoded() string {
	if x != nil {
		return x.Coded
	}
	return ""
}

type Participant struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	UserId        string          `protobuf:"bytes,1,opt,name=user_id,json=userId,proto3" json:"user_id,omitempty"`
	Subscriptions []*Subscription `protobuf:"bytes,2,rep,name=subscriptions,proto3" json:"subscriptions,omitempty"`
}

func (x *Participant) Reset() {
	*x = Participant{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[1]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Participant) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Participant) ProtoMessage() {}

func (x *Participant) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[1]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Participant.ProtoReflect.Descriptor instead.
func (*Participant) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{1}
}

func (x *Participant) GetUserId() string {
	if x != nil {
		return x.UserId
	}
	return ""
}

func (x *Participant) GetSubscriptions() []*Subscription {
	if x != nil {
		return x.Subscriptions
	}
	return nil
}

type CallState struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	Participants []*Participant `protobuf:"bytes,1,rep,name=participants,proto3" json:"participants,omitempty"`
	Sequence     int64          `protobuf:"varint,2,opt,name=sequence,proto3" json:"sequence,omitempty"`
}

func (x *CallState) Reset() {
	*x = CallState{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[2]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *CallState) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*CallState) ProtoMessage() {}

func (x *CallState) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[2]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use CallState.ProtoReflect.Descriptor instead.
func (*CallState) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{2}
}

func (x *CallState) GetParticipants() []*Participant {
	if x != nil {
		return x.Participants
	}
	return nil
}

func (x *CallState) GetSequence() int64 {
	if x != nil {
		return x.Sequence
	}
	return 0
}

type JoinCallRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	CallCid    string     `protobuf:"bytes,1,opt,name=call_cid,json=callCid,proto3" json:"call_cid,omitempty"`
	LocalState *CallState `protobuf:"bytes,2,opt,name=local_state,json=localState,proto3" json:"local_state,omitempty"`
}

func (x *JoinCallRequest) Reset() {
	*x = JoinCallRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[3]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *JoinCallRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*JoinCallRequest) ProtoMessage() {}

func (x *JoinCallRequest) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[3]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use JoinCallRequest.ProtoReflect.Descriptor instead.
func (*JoinCallRequest) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{3}
}

func (x *JoinCallRequest) GetCallCid() string {
	if x != nil {
		return x.CallCid
	}
	return ""
}

func (x *JoinCallRequest) GetLocalState() *CallState {
	if x != nil {
		return x.LocalState
	}
	return nil
}

type JoinCallResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	SfuAddresses []string   `protobuf:"bytes,1,rep,name=sfu_addresses,json=sfuAddresses,proto3" json:"sfu_addresses,omitempty"`
	LocalState   *CallState `protobuf:"bytes,2,opt,name=local_state,json=localState,proto3" json:"local_state,omitempty"`
}

func (x *JoinCallResponse) Reset() {
	*x = JoinCallResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[4]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *JoinCallResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*JoinCallResponse) ProtoMessage() {}

func (x *JoinCallResponse) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[4]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use JoinCallResponse.ProtoReflect.Descriptor instead.
func (*JoinCallResponse) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{4}
}

func (x *JoinCallResponse) GetSfuAddresses() []string {
	if x != nil {
		return x.SfuAddresses
	}
	return nil
}

func (x *JoinCallResponse) GetLocalState() *CallState {
	if x != nil {
		return x.LocalState
	}
	return nil
}

type GetCallStateRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *GetCallStateRequest) Reset() {
	*x = GetCallStateRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[5]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetCallStateRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetCallStateRequest) ProtoMessage() {}

func (x *GetCallStateRequest) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[5]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetCallStateRequest.ProtoReflect.Descriptor instead.
func (*GetCallStateRequest) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{5}
}

type GetCallStateResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	LocalState *CallState `protobuf:"bytes,1,opt,name=local_state,json=localState,proto3" json:"local_state,omitempty"`
}

func (x *GetCallStateResponse) Reset() {
	*x = GetCallStateResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[6]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *GetCallStateResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*GetCallStateResponse) ProtoMessage() {}

func (x *GetCallStateResponse) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[6]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use GetCallStateResponse.ProtoReflect.Descriptor instead.
func (*GetCallStateResponse) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{6}
}

func (x *GetCallStateResponse) GetLocalState() *CallState {
	if x != nil {
		return x.LocalState
	}
	return nil
}

type AudioMuteChanged struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *AudioMuteChanged) Reset() {
	*x = AudioMuteChanged{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[7]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *AudioMuteChanged) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*AudioMuteChanged) ProtoMessage() {}

func (x *AudioMuteChanged) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[7]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use AudioMuteChanged.ProtoReflect.Descriptor instead.
func (*AudioMuteChanged) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{7}
}

type Healthcheck struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *Healthcheck) Reset() {
	*x = Healthcheck{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[8]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *Healthcheck) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*Healthcheck) ProtoMessage() {}

func (x *Healthcheck) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[8]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use Healthcheck.ProtoReflect.Descriptor instead.
func (*Healthcheck) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{8}
}

type VideoMuteChanged struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *VideoMuteChanged) Reset() {
	*x = VideoMuteChanged{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[9]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *VideoMuteChanged) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*VideoMuteChanged) ProtoMessage() {}

func (x *VideoMuteChanged) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[9]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use VideoMuteChanged.ProtoReflect.Descriptor instead.
func (*VideoMuteChanged) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{9}
}

type SendEventRequest struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields

	// sequence is specific to the local state of the SFU sending the event
	// receivers can use this to detect out of order delivery or lost messages
	// when that happens
	CallCid  string `protobuf:"bytes,1,opt,name=call_cid,json=callCid,proto3" json:"call_cid,omitempty"`
	Sequence int64  `protobuf:"varint,2,opt,name=sequence,proto3" json:"sequence,omitempty"`
	// Types that are assignable to Event:
	//
	//	*SendEventRequest_HealthCheck
	//	*SendEventRequest_AudioMuteChanged
	//	*SendEventRequest_VideoMuteChanged
	Event isSendEventRequest_Event `protobuf_oneof:"event"`
}

func (x *SendEventRequest) Reset() {
	*x = SendEventRequest{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[10]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *SendEventRequest) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*SendEventRequest) ProtoMessage() {}

func (x *SendEventRequest) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[10]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use SendEventRequest.ProtoReflect.Descriptor instead.
func (*SendEventRequest) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{10}
}

func (x *SendEventRequest) GetCallCid() string {
	if x != nil {
		return x.CallCid
	}
	return ""
}

func (x *SendEventRequest) GetSequence() int64 {
	if x != nil {
		return x.Sequence
	}
	return 0
}

func (m *SendEventRequest) GetEvent() isSendEventRequest_Event {
	if m != nil {
		return m.Event
	}
	return nil
}

func (x *SendEventRequest) GetHealthCheck() *Healthcheck {
	if x, ok := x.GetEvent().(*SendEventRequest_HealthCheck); ok {
		return x.HealthCheck
	}
	return nil
}

func (x *SendEventRequest) GetAudioMuteChanged() *AudioMuteChanged {
	if x, ok := x.GetEvent().(*SendEventRequest_AudioMuteChanged); ok {
		return x.AudioMuteChanged
	}
	return nil
}

func (x *SendEventRequest) GetVideoMuteChanged() *VideoMuteChanged {
	if x, ok := x.GetEvent().(*SendEventRequest_VideoMuteChanged); ok {
		return x.VideoMuteChanged
	}
	return nil
}

type isSendEventRequest_Event interface {
	isSendEventRequest_Event()
}

type SendEventRequest_HealthCheck struct {
	HealthCheck *Healthcheck `protobuf:"bytes,3,opt,name=health_check,json=healthCheck,proto3,oneof"`
}

type SendEventRequest_AudioMuteChanged struct {
	AudioMuteChanged *AudioMuteChanged `protobuf:"bytes,4,opt,name=audio_mute_changed,json=audioMuteChanged,proto3,oneof"`
}

type SendEventRequest_VideoMuteChanged struct {
	VideoMuteChanged *VideoMuteChanged `protobuf:"bytes,5,opt,name=video_mute_changed,json=videoMuteChanged,proto3,oneof"`
}

func (*SendEventRequest_HealthCheck) isSendEventRequest_Event() {}

func (*SendEventRequest_AudioMuteChanged) isSendEventRequest_Event() {}

func (*SendEventRequest_VideoMuteChanged) isSendEventRequest_Event() {}

type SendEventResponse struct {
	state         protoimpl.MessageState
	sizeCache     protoimpl.SizeCache
	unknownFields protoimpl.UnknownFields
}

func (x *SendEventResponse) Reset() {
	*x = SendEventResponse{}
	if protoimpl.UnsafeEnabled {
		mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[11]
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		ms.StoreMessageInfo(mi)
	}
}

func (x *SendEventResponse) String() string {
	return protoimpl.X.MessageStringOf(x)
}

func (*SendEventResponse) ProtoMessage() {}

func (x *SendEventResponse) ProtoReflect() protoreflect.Message {
	mi := &file_video_sfu_cascading_rpc_gossip_proto_msgTypes[11]
	if protoimpl.UnsafeEnabled && x != nil {
		ms := protoimpl.X.MessageStateOf(protoimpl.Pointer(x))
		if ms.LoadMessageInfo() == nil {
			ms.StoreMessageInfo(mi)
		}
		return ms
	}
	return mi.MessageOf(x)
}

// Deprecated: Use SendEventResponse.ProtoReflect.Descriptor instead.
func (*SendEventResponse) Descriptor() ([]byte, []int) {
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP(), []int{11}
}

var File_video_sfu_cascading_rpc_gossip_proto protoreflect.FileDescriptor

var file_video_sfu_cascading_rpc_gossip_proto_rawDesc = []byte{
	0x0a, 0x24, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2f, 0x73, 0x66, 0x75, 0x2f, 0x63, 0x61, 0x73, 0x63,
	0x61, 0x64, 0x69, 0x6e, 0x67, 0x5f, 0x72, 0x70, 0x63, 0x2f, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70,
	0x2e, 0x70, 0x72, 0x6f, 0x74, 0x6f, 0x12, 0x17, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76,
	0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x22,
	0x59, 0x0a, 0x0c, 0x53, 0x75, 0x62, 0x73, 0x63, 0x72, 0x69, 0x70, 0x74, 0x69, 0x6f, 0x6e, 0x12,
	0x21, 0x0a, 0x0c, 0x70, 0x75, 0x62, 0x6c, 0x69, 0x73, 0x68, 0x65, 0x72, 0x5f, 0x69, 0x64, 0x18,
	0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x0b, 0x70, 0x75, 0x62, 0x6c, 0x69, 0x73, 0x68, 0x65, 0x72,
	0x49, 0x64, 0x12, 0x10, 0x0a, 0x03, 0x72, 0x69, 0x64, 0x18, 0x02, 0x20, 0x01, 0x28, 0x09, 0x52,
	0x03, 0x72, 0x69, 0x64, 0x12, 0x14, 0x0a, 0x05, 0x63, 0x6f, 0x64, 0x65, 0x64, 0x18, 0x03, 0x20,
	0x01, 0x28, 0x09, 0x52, 0x05, 0x63, 0x6f, 0x64, 0x65, 0x64, 0x22, 0x73, 0x0a, 0x0b, 0x50, 0x61,
	0x72, 0x74, 0x69, 0x63, 0x69, 0x70, 0x61, 0x6e, 0x74, 0x12, 0x17, 0x0a, 0x07, 0x75, 0x73, 0x65,
	0x72, 0x5f, 0x69, 0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x06, 0x75, 0x73, 0x65, 0x72,
	0x49, 0x64, 0x12, 0x4b, 0x0a, 0x0d, 0x73, 0x75, 0x62, 0x73, 0x63, 0x72, 0x69, 0x70, 0x74, 0x69,
	0x6f, 0x6e, 0x73, 0x18, 0x02, 0x20, 0x03, 0x28, 0x0b, 0x32, 0x25, 0x2e, 0x73, 0x74, 0x72, 0x65,
	0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73,
	0x73, 0x69, 0x70, 0x2e, 0x53, 0x75, 0x62, 0x73, 0x63, 0x72, 0x69, 0x70, 0x74, 0x69, 0x6f, 0x6e,
	0x52, 0x0d, 0x73, 0x75, 0x62, 0x73, 0x63, 0x72, 0x69, 0x70, 0x74, 0x69, 0x6f, 0x6e, 0x73, 0x22,
	0x71, 0x0a, 0x09, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x12, 0x48, 0x0a, 0x0c,
	0x70, 0x61, 0x72, 0x74, 0x69, 0x63, 0x69, 0x70, 0x61, 0x6e, 0x74, 0x73, 0x18, 0x01, 0x20, 0x03,
	0x28, 0x0b, 0x32, 0x24, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65,
	0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x50, 0x61, 0x72,
	0x74, 0x69, 0x63, 0x69, 0x70, 0x61, 0x6e, 0x74, 0x52, 0x0c, 0x70, 0x61, 0x72, 0x74, 0x69, 0x63,
	0x69, 0x70, 0x61, 0x6e, 0x74, 0x73, 0x12, 0x1a, 0x0a, 0x08, 0x73, 0x65, 0x71, 0x75, 0x65, 0x6e,
	0x63, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03, 0x52, 0x08, 0x73, 0x65, 0x71, 0x75, 0x65, 0x6e,
	0x63, 0x65, 0x22, 0x71, 0x0a, 0x0f, 0x4a, 0x6f, 0x69, 0x6e, 0x43, 0x61, 0x6c, 0x6c, 0x52, 0x65,
	0x71, 0x75, 0x65, 0x73, 0x74, 0x12, 0x19, 0x0a, 0x08, 0x63, 0x61, 0x6c, 0x6c, 0x5f, 0x63, 0x69,
	0x64, 0x18, 0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x07, 0x63, 0x61, 0x6c, 0x6c, 0x43, 0x69, 0x64,
	0x12, 0x43, 0x0a, 0x0b, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x5f, 0x73, 0x74, 0x61, 0x74, 0x65, 0x18,
	0x02, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x22, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76,
	0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e,
	0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x52, 0x0a, 0x6c, 0x6f, 0x63, 0x61, 0x6c,
	0x53, 0x74, 0x61, 0x74, 0x65, 0x22, 0x7c, 0x0a, 0x10, 0x4a, 0x6f, 0x69, 0x6e, 0x43, 0x61, 0x6c,
	0x6c, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x23, 0x0a, 0x0d, 0x73, 0x66, 0x75,
	0x5f, 0x61, 0x64, 0x64, 0x72, 0x65, 0x73, 0x73, 0x65, 0x73, 0x18, 0x01, 0x20, 0x03, 0x28, 0x09,
	0x52, 0x0c, 0x73, 0x66, 0x75, 0x41, 0x64, 0x64, 0x72, 0x65, 0x73, 0x73, 0x65, 0x73, 0x12, 0x43,
	0x0a, 0x0b, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x5f, 0x73, 0x74, 0x61, 0x74, 0x65, 0x18, 0x02, 0x20,
	0x01, 0x28, 0x0b, 0x32, 0x22, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64,
	0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x43, 0x61,
	0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x52, 0x0a, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x53, 0x74,
	0x61, 0x74, 0x65, 0x22, 0x15, 0x0a, 0x13, 0x47, 0x65, 0x74, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74,
	0x61, 0x74, 0x65, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x22, 0x5b, 0x0a, 0x14, 0x47, 0x65,
	0x74, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e,
	0x73, 0x65, 0x12, 0x43, 0x0a, 0x0b, 0x6c, 0x6f, 0x63, 0x61, 0x6c, 0x5f, 0x73, 0x74, 0x61, 0x74,
	0x65, 0x18, 0x01, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x22, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d,
	0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69,
	0x70, 0x2e, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x52, 0x0a, 0x6c, 0x6f, 0x63,
	0x61, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x22, 0x12, 0x0a, 0x10, 0x41, 0x75, 0x64, 0x69, 0x6f,
	0x4d, 0x75, 0x74, 0x65, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x22, 0x0d, 0x0a, 0x0b, 0x48,
	0x65, 0x61, 0x6c, 0x74, 0x68, 0x63, 0x68, 0x65, 0x63, 0x6b, 0x22, 0x12, 0x0a, 0x10, 0x56, 0x69,
	0x64, 0x65, 0x6f, 0x4d, 0x75, 0x74, 0x65, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x22, 0xd3,
	0x02, 0x0a, 0x10, 0x53, 0x65, 0x6e, 0x64, 0x45, 0x76, 0x65, 0x6e, 0x74, 0x52, 0x65, 0x71, 0x75,
	0x65, 0x73, 0x74, 0x12, 0x19, 0x0a, 0x08, 0x63, 0x61, 0x6c, 0x6c, 0x5f, 0x63, 0x69, 0x64, 0x18,
	0x01, 0x20, 0x01, 0x28, 0x09, 0x52, 0x07, 0x63, 0x61, 0x6c, 0x6c, 0x43, 0x69, 0x64, 0x12, 0x1a,
	0x0a, 0x08, 0x73, 0x65, 0x71, 0x75, 0x65, 0x6e, 0x63, 0x65, 0x18, 0x02, 0x20, 0x01, 0x28, 0x03,
	0x52, 0x08, 0x73, 0x65, 0x71, 0x75, 0x65, 0x6e, 0x63, 0x65, 0x12, 0x49, 0x0a, 0x0c, 0x68, 0x65,
	0x61, 0x6c, 0x74, 0x68, 0x5f, 0x63, 0x68, 0x65, 0x63, 0x6b, 0x18, 0x03, 0x20, 0x01, 0x28, 0x0b,
	0x32, 0x24, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e,
	0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x48, 0x65, 0x61, 0x6c, 0x74,
	0x68, 0x63, 0x68, 0x65, 0x63, 0x6b, 0x48, 0x00, 0x52, 0x0b, 0x68, 0x65, 0x61, 0x6c, 0x74, 0x68,
	0x43, 0x68, 0x65, 0x63, 0x6b, 0x12, 0x59, 0x0a, 0x12, 0x61, 0x75, 0x64, 0x69, 0x6f, 0x5f, 0x6d,
	0x75, 0x74, 0x65, 0x5f, 0x63, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x18, 0x04, 0x20, 0x01, 0x28,
	0x0b, 0x32, 0x29, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f,
	0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x41, 0x75, 0x64, 0x69,
	0x6f, 0x4d, 0x75, 0x74, 0x65, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x48, 0x00, 0x52, 0x10,
	0x61, 0x75, 0x64, 0x69, 0x6f, 0x4d, 0x75, 0x74, 0x65, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64,
	0x12, 0x59, 0x0a, 0x12, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x5f, 0x6d, 0x75, 0x74, 0x65, 0x5f, 0x63,
	0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x18, 0x05, 0x20, 0x01, 0x28, 0x0b, 0x32, 0x29, 0x2e, 0x73,
	0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e,
	0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x56, 0x69, 0x64, 0x65, 0x6f, 0x4d, 0x75, 0x74, 0x65,
	0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x48, 0x00, 0x52, 0x10, 0x76, 0x69, 0x64, 0x65, 0x6f,
	0x4d, 0x75, 0x74, 0x65, 0x43, 0x68, 0x61, 0x6e, 0x67, 0x65, 0x64, 0x42, 0x07, 0x0a, 0x05, 0x65,
	0x76, 0x65, 0x6e, 0x74, 0x22, 0x13, 0x0a, 0x11, 0x53, 0x65, 0x6e, 0x64, 0x45, 0x76, 0x65, 0x6e,
	0x74, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x32, 0xbd, 0x02, 0x0a, 0x09, 0x47, 0x6f,
	0x73, 0x73, 0x69, 0x70, 0x52, 0x50, 0x43, 0x12, 0x5f, 0x0a, 0x08, 0x4a, 0x6f, 0x69, 0x6e, 0x43,
	0x61, 0x6c, 0x6c, 0x12, 0x28, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64,
	0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x4a, 0x6f,
	0x69, 0x6e, 0x43, 0x61, 0x6c, 0x6c, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x29, 0x2e,
	0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75,
	0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x4a, 0x6f, 0x69, 0x6e, 0x43, 0x61, 0x6c, 0x6c,
	0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x62, 0x0a, 0x09, 0x53, 0x65, 0x6e, 0x64,
	0x45, 0x76, 0x65, 0x6e, 0x74, 0x12, 0x29, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76,
	0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e,
	0x53, 0x65, 0x6e, 0x64, 0x45, 0x76, 0x65, 0x6e, 0x74, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74,
	0x1a, 0x2a, 0x2e, 0x73, 0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e,
	0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x53, 0x65, 0x6e, 0x64, 0x45,
	0x76, 0x65, 0x6e, 0x74, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x12, 0x6b, 0x0a, 0x0c,
	0x47, 0x65, 0x74, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74, 0x65, 0x12, 0x2c, 0x2e, 0x73,
	0x74, 0x72, 0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e,
	0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x2e, 0x47, 0x65, 0x74, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74,
	0x61, 0x74, 0x65, 0x52, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x1a, 0x2d, 0x2e, 0x73, 0x74, 0x72,
	0x65, 0x61, 0x6d, 0x2e, 0x76, 0x69, 0x64, 0x65, 0x6f, 0x2e, 0x73, 0x66, 0x75, 0x2e, 0x67, 0x6f,
	0x73, 0x73, 0x69, 0x70, 0x2e, 0x47, 0x65, 0x74, 0x43, 0x61, 0x6c, 0x6c, 0x53, 0x74, 0x61, 0x74,
	0x65, 0x52, 0x65, 0x73, 0x70, 0x6f, 0x6e, 0x73, 0x65, 0x42, 0x10, 0x5a, 0x0e, 0x73, 0x66, 0x75,
	0x5f, 0x67, 0x6f, 0x73, 0x73, 0x69, 0x70, 0x5f, 0x72, 0x70, 0x63, 0x62, 0x06, 0x70, 0x72, 0x6f,
	0x74, 0x6f, 0x33,
}

var (
	file_video_sfu_cascading_rpc_gossip_proto_rawDescOnce sync.Once
	file_video_sfu_cascading_rpc_gossip_proto_rawDescData = file_video_sfu_cascading_rpc_gossip_proto_rawDesc
)

func file_video_sfu_cascading_rpc_gossip_proto_rawDescGZIP() []byte {
	file_video_sfu_cascading_rpc_gossip_proto_rawDescOnce.Do(func() {
		file_video_sfu_cascading_rpc_gossip_proto_rawDescData = protoimpl.X.CompressGZIP(file_video_sfu_cascading_rpc_gossip_proto_rawDescData)
	})
	return file_video_sfu_cascading_rpc_gossip_proto_rawDescData
}

var file_video_sfu_cascading_rpc_gossip_proto_msgTypes = make([]protoimpl.MessageInfo, 12)
var file_video_sfu_cascading_rpc_gossip_proto_goTypes = []interface{}{
	(*Subscription)(nil),         // 0: stream.video.sfu.gossip.Subscription
	(*Participant)(nil),          // 1: stream.video.sfu.gossip.Participant
	(*CallState)(nil),            // 2: stream.video.sfu.gossip.CallState
	(*JoinCallRequest)(nil),      // 3: stream.video.sfu.gossip.JoinCallRequest
	(*JoinCallResponse)(nil),     // 4: stream.video.sfu.gossip.JoinCallResponse
	(*GetCallStateRequest)(nil),  // 5: stream.video.sfu.gossip.GetCallStateRequest
	(*GetCallStateResponse)(nil), // 6: stream.video.sfu.gossip.GetCallStateResponse
	(*AudioMuteChanged)(nil),     // 7: stream.video.sfu.gossip.AudioMuteChanged
	(*Healthcheck)(nil),          // 8: stream.video.sfu.gossip.Healthcheck
	(*VideoMuteChanged)(nil),     // 9: stream.video.sfu.gossip.VideoMuteChanged
	(*SendEventRequest)(nil),     // 10: stream.video.sfu.gossip.SendEventRequest
	(*SendEventResponse)(nil),    // 11: stream.video.sfu.gossip.SendEventResponse
}
var file_video_sfu_cascading_rpc_gossip_proto_depIdxs = []int32{
	0,  // 0: stream.video.sfu.gossip.Participant.subscriptions:type_name -> stream.video.sfu.gossip.Subscription
	1,  // 1: stream.video.sfu.gossip.CallState.participants:type_name -> stream.video.sfu.gossip.Participant
	2,  // 2: stream.video.sfu.gossip.JoinCallRequest.local_state:type_name -> stream.video.sfu.gossip.CallState
	2,  // 3: stream.video.sfu.gossip.JoinCallResponse.local_state:type_name -> stream.video.sfu.gossip.CallState
	2,  // 4: stream.video.sfu.gossip.GetCallStateResponse.local_state:type_name -> stream.video.sfu.gossip.CallState
	8,  // 5: stream.video.sfu.gossip.SendEventRequest.health_check:type_name -> stream.video.sfu.gossip.Healthcheck
	7,  // 6: stream.video.sfu.gossip.SendEventRequest.audio_mute_changed:type_name -> stream.video.sfu.gossip.AudioMuteChanged
	9,  // 7: stream.video.sfu.gossip.SendEventRequest.video_mute_changed:type_name -> stream.video.sfu.gossip.VideoMuteChanged
	3,  // 8: stream.video.sfu.gossip.GossipRPC.JoinCall:input_type -> stream.video.sfu.gossip.JoinCallRequest
	10, // 9: stream.video.sfu.gossip.GossipRPC.SendEvent:input_type -> stream.video.sfu.gossip.SendEventRequest
	5,  // 10: stream.video.sfu.gossip.GossipRPC.GetCallState:input_type -> stream.video.sfu.gossip.GetCallStateRequest
	4,  // 11: stream.video.sfu.gossip.GossipRPC.JoinCall:output_type -> stream.video.sfu.gossip.JoinCallResponse
	11, // 12: stream.video.sfu.gossip.GossipRPC.SendEvent:output_type -> stream.video.sfu.gossip.SendEventResponse
	6,  // 13: stream.video.sfu.gossip.GossipRPC.GetCallState:output_type -> stream.video.sfu.gossip.GetCallStateResponse
	11, // [11:14] is the sub-list for method output_type
	8,  // [8:11] is the sub-list for method input_type
	8,  // [8:8] is the sub-list for extension type_name
	8,  // [8:8] is the sub-list for extension extendee
	0,  // [0:8] is the sub-list for field type_name
}

func init() { file_video_sfu_cascading_rpc_gossip_proto_init() }
func file_video_sfu_cascading_rpc_gossip_proto_init() {
	if File_video_sfu_cascading_rpc_gossip_proto != nil {
		return
	}
	if !protoimpl.UnsafeEnabled {
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[0].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*Subscription); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[1].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*Participant); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[2].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*CallState); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[3].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*JoinCallRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[4].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*JoinCallResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[5].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetCallStateRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[6].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*GetCallStateResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[7].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*AudioMuteChanged); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[8].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*Healthcheck); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[9].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*VideoMuteChanged); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[10].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*SendEventRequest); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
		file_video_sfu_cascading_rpc_gossip_proto_msgTypes[11].Exporter = func(v interface{}, i int) interface{} {
			switch v := v.(*SendEventResponse); i {
			case 0:
				return &v.state
			case 1:
				return &v.sizeCache
			case 2:
				return &v.unknownFields
			default:
				return nil
			}
		}
	}
	file_video_sfu_cascading_rpc_gossip_proto_msgTypes[10].OneofWrappers = []interface{}{
		(*SendEventRequest_HealthCheck)(nil),
		(*SendEventRequest_AudioMuteChanged)(nil),
		(*SendEventRequest_VideoMuteChanged)(nil),
	}
	type x struct{}
	out := protoimpl.TypeBuilder{
		File: protoimpl.DescBuilder{
			GoPackagePath: reflect.TypeOf(x{}).PkgPath(),
			RawDescriptor: file_video_sfu_cascading_rpc_gossip_proto_rawDesc,
			NumEnums:      0,
			NumMessages:   12,
			NumExtensions: 0,
			NumServices:   1,
		},
		GoTypes:           file_video_sfu_cascading_rpc_gossip_proto_goTypes,
		DependencyIndexes: file_video_sfu_cascading_rpc_gossip_proto_depIdxs,
		MessageInfos:      file_video_sfu_cascading_rpc_gossip_proto_msgTypes,
	}.Build()
	File_video_sfu_cascading_rpc_gossip_proto = out.File
	file_video_sfu_cascading_rpc_gossip_proto_rawDesc = nil
	file_video_sfu_cascading_rpc_gossip_proto_goTypes = nil
	file_video_sfu_cascading_rpc_gossip_proto_depIdxs = nil
}
