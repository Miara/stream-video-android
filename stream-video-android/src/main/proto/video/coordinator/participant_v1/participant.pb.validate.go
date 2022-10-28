// Code generated by protoc-gen-validate. DO NOT EDIT.
// source: video/coordinator/participant_v1/participant.proto

package participant_v1

import (
	"bytes"
	"errors"
	"fmt"
	"net"
	"net/mail"
	"net/url"
	"regexp"
	"sort"
	"strings"
	"time"
	"unicode/utf8"

	"google.golang.org/protobuf/types/known/anypb"
)

// ensure the imports are used
var (
	_ = bytes.MinRead
	_ = errors.New("")
	_ = fmt.Print
	_ = utf8.UTFMax
	_ = (*regexp.Regexp)(nil)
	_ = (*strings.Reader)(nil)
	_ = net.IPv4len
	_ = time.Duration(0)
	_ = (*url.URL)(nil)
	_ = (*mail.Address)(nil)
	_ = anypb.Any{}
	_ = sort.Sort
)

// Validate checks the field values on Participant with the rules defined in
// the proto definition for this message. If any rules are violated, the first
// error encountered is returned, or nil if there are no violations.
func (m *Participant) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on Participant with the rules defined in
// the proto definition for this message. If any rules are violated, the
// result is a list of violation errors wrapped in ParticipantMultiError, or
// nil if none found.
func (m *Participant) ValidateAll() error {
	return m.validate(true)
}

func (m *Participant) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	if all {
		switch v := interface{}(m.GetUser()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, ParticipantValidationError{
					field:  "User",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, ParticipantValidationError{
					field:  "User",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetUser()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return ParticipantValidationError{
				field:  "User",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	// no validation rules for Role

	// no validation rules for Online

	// no validation rules for CustomJson

	// no validation rules for Video

	// no validation rules for Audio

	if all {
		switch v := interface{}(m.GetCreatedAt()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, ParticipantValidationError{
					field:  "CreatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, ParticipantValidationError{
					field:  "CreatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetCreatedAt()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return ParticipantValidationError{
				field:  "CreatedAt",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	if all {
		switch v := interface{}(m.GetUpdatedAt()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, ParticipantValidationError{
					field:  "UpdatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, ParticipantValidationError{
					field:  "UpdatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetUpdatedAt()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return ParticipantValidationError{
				field:  "UpdatedAt",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	if len(errors) > 0 {
		return ParticipantMultiError(errors)
	}

	return nil
}

// ParticipantMultiError is an error wrapping multiple validation errors
// returned by Participant.ValidateAll() if the designated constraints aren't met.
type ParticipantMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m ParticipantMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m ParticipantMultiError) AllErrors() []error { return m }

// ParticipantValidationError is the validation error returned by
// Participant.Validate if the designated constraints aren't met.
type ParticipantValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e ParticipantValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e ParticipantValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e ParticipantValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e ParticipantValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e ParticipantValidationError) ErrorName() string { return "ParticipantValidationError" }

// Error satisfies the builtin error interface
func (e ParticipantValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sParticipant.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = ParticipantValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = ParticipantValidationError{}

// Validate checks the field values on ParticipantRequest with the rules
// defined in the proto definition for this message. If any rules are
// violated, the first error encountered is returned, or nil if there are no violations.
func (m *ParticipantRequest) Validate() error {
	return m.validate(false)
}

// ValidateAll checks the field values on ParticipantRequest with the rules
// defined in the proto definition for this message. If any rules are
// violated, the result is a list of violation errors wrapped in
// ParticipantRequestMultiError, or nil if none found.
func (m *ParticipantRequest) ValidateAll() error {
	return m.validate(true)
}

func (m *ParticipantRequest) validate(all bool) error {
	if m == nil {
		return nil
	}

	var errors []error

	// no validation rules for UserId

	// no validation rules for Role

	// no validation rules for CustomJson

	if all {
		switch v := interface{}(m.GetCreatedAt()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, ParticipantRequestValidationError{
					field:  "CreatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, ParticipantRequestValidationError{
					field:  "CreatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetCreatedAt()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return ParticipantRequestValidationError{
				field:  "CreatedAt",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	if all {
		switch v := interface{}(m.GetUpdatedAt()).(type) {
		case interface{ ValidateAll() error }:
			if err := v.ValidateAll(); err != nil {
				errors = append(errors, ParticipantRequestValidationError{
					field:  "UpdatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		case interface{ Validate() error }:
			if err := v.Validate(); err != nil {
				errors = append(errors, ParticipantRequestValidationError{
					field:  "UpdatedAt",
					reason: "embedded message failed validation",
					cause:  err,
				})
			}
		}
	} else if v, ok := interface{}(m.GetUpdatedAt()).(interface{ Validate() error }); ok {
		if err := v.Validate(); err != nil {
			return ParticipantRequestValidationError{
				field:  "UpdatedAt",
				reason: "embedded message failed validation",
				cause:  err,
			}
		}
	}

	if len(errors) > 0 {
		return ParticipantRequestMultiError(errors)
	}

	return nil
}

// ParticipantRequestMultiError is an error wrapping multiple validation errors
// returned by ParticipantRequest.ValidateAll() if the designated constraints
// aren't met.
type ParticipantRequestMultiError []error

// Error returns a concatenation of all the error messages it wraps.
func (m ParticipantRequestMultiError) Error() string {
	var msgs []string
	for _, err := range m {
		msgs = append(msgs, err.Error())
	}
	return strings.Join(msgs, "; ")
}

// AllErrors returns a list of validation violation errors.
func (m ParticipantRequestMultiError) AllErrors() []error { return m }

// ParticipantRequestValidationError is the validation error returned by
// ParticipantRequest.Validate if the designated constraints aren't met.
type ParticipantRequestValidationError struct {
	field  string
	reason string
	cause  error
	key    bool
}

// Field function returns field value.
func (e ParticipantRequestValidationError) Field() string { return e.field }

// Reason function returns reason value.
func (e ParticipantRequestValidationError) Reason() string { return e.reason }

// Cause function returns cause value.
func (e ParticipantRequestValidationError) Cause() error { return e.cause }

// Key function returns key value.
func (e ParticipantRequestValidationError) Key() bool { return e.key }

// ErrorName returns error name.
func (e ParticipantRequestValidationError) ErrorName() string {
	return "ParticipantRequestValidationError"
}

// Error satisfies the builtin error interface
func (e ParticipantRequestValidationError) Error() string {
	cause := ""
	if e.cause != nil {
		cause = fmt.Sprintf(" | caused by: %v", e.cause)
	}

	key := ""
	if e.key {
		key = "key for "
	}

	return fmt.Sprintf(
		"invalid %sParticipantRequest.%s: %s%s",
		key,
		e.field,
		e.reason,
		cause)
}

var _ error = ParticipantRequestValidationError{}

var _ interface {
	Field() string
	Reason() string
	Key() bool
	Cause() error
	ErrorName() string
} = ParticipantRequestValidationError{}
