using DIYHomeAutomationAPI.Models.Enums;

namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Device
    /// </summary>
    public class Device
    {
        /// <summary>
        /// Device's Id
        /// </summary>
        public int? Id { get; set; }

        /// <summary>
        /// Device's Name
        /// </summary>
        public string Name { get; set; } = null!;

        /// <summary>
        /// Device's PinValue
        /// </summary>
        public string? PinValue { get; set; } = null!;

        /// <summary>
        /// Device's State
        /// </summary>
        public bool? State { get; set; }

        /// <summary>
        /// Device's Value
        /// </summary>
        public double? Value { get; set; }        

        /// <summary>
        /// Device's Icon
        /// </summary>
        public string? Icon { get; set; }

        /// <summary>
        /// Room's Id
        /// </summary>
        public int? RoomId { get; set; }

        /// <summary>
        /// Device's Type Id
        /// </summary>
        public int? TypeDeviceId { get; set; }

        /// <summary>
        /// List of TaskDevice's
        /// </summary>
        public List<TaskDevice>? TaskDevices { get; set; }
    }
}
