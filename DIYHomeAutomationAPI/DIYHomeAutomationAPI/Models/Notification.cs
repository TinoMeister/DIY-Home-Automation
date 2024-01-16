using DIYHomeAutomationAPI.Models.Enums;

namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a Notification
    /// </summary>
    public class Notification
    {
        /// <summary>
        /// Notification's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Notification's Name
        /// </summary>
        public string Name { get; set; } = null!;

        /// <summary>
        /// Notification's Description
        /// </summary>
        public string Description { get; set; } = null!;

        /// <summary>
        /// Notification's Date
        /// </summary>
        public DateTime Time { get; set; }

        /// <summary>
        /// Notification's visualization state
        /// </summary>
        public bool VisualizeState { get; set; }

        /// <summary>
        /// Task's Id
        /// </summary>
        public int? TaskId { get; set; }

        /// <summary>
        /// Device's Id
        /// </summary>
        public int? DeviceId { get; set; }

    }
}
